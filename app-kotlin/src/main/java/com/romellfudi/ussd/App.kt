/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd

import android.app.Application
import com.romellfudi.ussd.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * @autor Romell Domínguez
 * @date 2020-04-20
 * @version 1.0
 */
class App : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Any>


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.factory()
                .create(this)
                .inject(this)
    }

    override fun androidInjector() = activityDispatchingAndroidInjector
}