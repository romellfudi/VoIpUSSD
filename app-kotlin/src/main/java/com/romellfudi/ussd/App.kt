/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd

import android.app.Application
import com.airbnb.lottie.L
import com.romellfudi.ussd.accessibility.di.accessibilityModule
import com.romellfudi.ussd.main.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * @autor Romell Domínguez
 * @date 2020-04-20
 * @version 1.0
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            L.DBG = true
            Timber.plant(object : Timber.DebugTree(){
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    super.log(priority, "timber_tag_$tag", message, t)
                }

                override fun createStackElementTag(element: StackTraceElement): String? {
                    return String.format(
                        "%s:%s",
                        element.methodName,
                        element.lineNumber,
                        super.createStackElementTag(element))
                }
            })
        }
        startKoin {
            printLogger()
            androidContext(this@App)
            modules(
                    appModule,
                    accessibilityModule
            )
        }
    }
}