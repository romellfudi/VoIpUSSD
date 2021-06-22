/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd

import android.app.Application
import com.airbnb.lottie.L
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.romellfudi.ussd.accessibility.di.accessibilityModule
import com.romellfudi.ussd.main.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Specially check the logcat in DEBUG flavor
 *
 * @autor Romell Domínguez
 * @date 2020-04-20
 * @version 1.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1) // (Optional) How many method line to show. Default 2
            .methodOffset(5) // Set methodOffset to 5 in order to hide internal method calls
            .tag("") // To replace the default PRETTY_LOGGER tag with a dash (-).
            .build() // CHECK THE Logcat /-
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, "$tag", message, t) // Must not drop $ format
            }
        })
        Timber.d("Init Application\nSetting Koin Architecture...")

        startKoin {
            if (BuildConfig.DEBUG) {
                L.DBG = true
                printLogger()
            }
            androidContext(this@App)
            modules(
                appModule,
                accessibilityModule
            )
        }
    }
}