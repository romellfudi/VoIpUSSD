/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.view

import android.annotation.SuppressLint
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.R
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

/**
 * Main Activity
 *
 * @author Romell Domínguez
 * @version 1.0.b 23/02/2017
 * @since 1.0
 */
const val REQUEST_CODE_FLEXIBLE_UPDATE: Int = 1234
class MainActivity : AppCompatActivity(), KoinComponent,
        InstallStateUpdatedListener, MainMVPView {

    private val appUpdateManager: AppUpdateManager by inject()

    private val restart by lazy { getString(R.string.restartUpdate) }
    private val downloaded by lazy { getString(R.string.downloaded) }
    private val errorUpdate by lazy { getString(R.string.errorUpdate) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        runBlocking {
            async {
                appUpdateManager.registerListener(this@MainActivity)
                checkUpdate()
            }
        }
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            showMessage(downloaded)
            notifyUser()
        }
    }

    @SuppressLint("StringFormatMatches")
    override fun checkUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            showMessage(getString(R.string.app_update_info, appUpdateInfo.updateAvailability(),
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)))
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                            AppUpdateType.IMMEDIATE, this, REQUEST_CODE_FLEXIBLE_UPDATE)
                } catch (e: SendIntentException) {
                    showMessage(errorUpdate)
                }
            }
        }
    }

    override fun showMessage(message: String) =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()

    override fun notifyUser() =
            Snackbar.make(findViewById(android.R.id.content), restart, Snackbar.LENGTH_INDEFINITE)
                    .setAction(restart) {
                        appUpdateManager.completeUpdate()
                        appUpdateManager.unregisterListener(this)
                    }.show()

    override fun onDestroy() {
        appUpdateManager.unregisterListener(this)
        super.onDestroy()
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
