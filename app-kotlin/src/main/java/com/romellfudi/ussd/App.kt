package com.romellfudi.ussd

import android.app.Application
import com.romellfudi.ussd.di.AppComponent
import com.romellfudi.ussd.di.DaggerAppComponent

/**
 * @autor Romell Domínguez
 * @date 2020-04-20
 * @version 1.0
 */
open class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}