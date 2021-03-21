/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.view

import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.rbddevs.splashy.Splashy
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.complete
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.*
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
    lateinit var permissionService: PermissionService

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    lateinit var appUpdateManager: AppUpdateManager

    @Inject
    lateinit var splashy: Splashy

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        splashy.complete(this::checkUpdate)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        permissionService.request(callback)
    }

    override fun onStateUpdate(state: InstallState?) {
        if (state?.installStatus() == InstallStatus.DOWNLOADED) {
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
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE,
                            this@MainActivity, REQUEST_CODE_FLEXIBLE_UPDATE)
                } catch (e: SendIntentException) {
                    showMessage("Request update error")
                }
            }
        }
    }

    override fun showMessage(message: String) =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()

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

    private val callback = object : PermissionService.Callback() {
        override fun onResponse(refusePermissions: ArrayList<String>?) {
            // EXCEPTIONAL EVENT
            refusePermissions?.remove("android.permission.SYSTEM_ALERT_WINDOW")
            // EXCEPTIONAL EVENT
            refusePermissions?.remove("android.permission.ACTION_MANAGE_OVERLAY_PERMISSION")
            if (!refusePermissions.isNullOrEmpty()) {
                showMessage(getString(R.string.refuse_permissions))
                Handler().postDelayed({ finish() }, 2000)
            } else appUpdateManager.registerListener(this@MainActivity)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) =
            PermissionService.handler(callback, grantResults, permissions)
}
