/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.component

import android.app.Application
import com.romellfudi.ussd.App
import com.romellfudi.ussd.di.AppModule
import com.romellfudi.ussd.di.ActivityFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton
/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityFactory::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Application): AppComponent
    }

    fun inject(app: App)
}
