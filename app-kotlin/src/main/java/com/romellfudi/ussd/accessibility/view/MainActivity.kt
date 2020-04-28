/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.view

import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.rbddevs.splashy.Splashy
import com.romellfudi.ussd.R
import com.romellfudi.ussd.main.view.MainFragmentView
import com.romellfudi.ussdlibrary.BuildConfig
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.app_bar_main_menu.*
import javax.inject.Inject


/**
 * Main Activity
 *
 * @author Romell Domínguez
 * @version 1.0.b 23/02/2017
 * @since 1.0
 */
const val REQUEST_CODE_FLEXIBLE_UPDATE: Int = 1234

class MainActivity : AppCompatActivity(), HasAndroidInjector,
        InstallStateUpdatedListener, MainMVPView {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) splashy()
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = getString(R.string.app_name)
        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragment_layout, MainFragmentView())
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            addToBackStack(null)
            commit()
        }
        appUpdateManager.registerListener(this)
    }

    private fun splashy() {
        Splashy(this@MainActivity)
                .setLogo(R.drawable.combine)
                .setLogoScaleType(ImageView.ScaleType.FIT_CENTER)
                .setAnimation(Splashy.Animation.GROW_LOGO_FROM_CENTER, 500)
                .setTitle(R.string.app_tittle)
                .setTitleColor(R.color.black)
                .setSubTitle("Version  " + BuildConfig.VERSION_NAME)
                .setProgressColor(R.color.black)
                .setBackgroundResource(R.color.splash)
                .setClickToHide(false)
                .setFullScreen(true)
                .setTime(1000)
                .showProgress(true)
                .show()
        Splashy.onComplete(object : Splashy.OnComplete {
            override fun onComplete() {
                checkUpdate()
            }
        })
    }

    override fun onStateUpdate(state: InstallState?) {
        if (state!!.installStatus() == InstallStatus.DOWNLOADED) {
            showMessage("Has been Downloaded!!!")
            notifyUser()
        }
    }

    override fun checkUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            showMessage("updateAvailability: " + appUpdateInfo.updateAvailability() +
                    " isUpdateTypeAllowed: " + appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                requestUpdate(appUpdateInfo)
            }
        }
    }

    override fun showMessage(message: String) =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()

    private fun requestUpdate(appUpdateInfo: AppUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo, AppUpdateType.IMMEDIATE,
                    this@MainActivity, REQUEST_CODE_FLEXIBLE_UPDATE)
        } catch (e: SendIntentException) {
            showMessage("Request update error")
        }
    }

    override fun notifyUser() =
            Snackbar.make(findViewById(android.R.id.content), "Restart to update", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Restart to update") {
                        appUpdateManager.completeUpdate()
                        appUpdateManager.unregisterListener(this@MainActivity)
                    }.show()

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(this@MainActivity)
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { result ->
            if (result.installStatus() == InstallStatus.DOWNLOADED) {
                notifyUser()
            }
        }
    }

    override fun onBackPressed() = finish()


}
