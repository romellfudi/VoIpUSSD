/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd

import android.app.Application
import com.romellfudi.ussd.di.component.AppComponent
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