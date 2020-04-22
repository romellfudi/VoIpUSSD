/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.sample

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import dagger.Module
import dagger.Provides

/**
 * @autor Romell Domínguez
 * @date 2020-04-21
 * @version 1.0
 */
@Module
class ActivityModule {

    @Provides // drop abstract
    @ActivityQualifier
    fun provideAppUpdateManager(context: Context): AppUpdateManager {
        return AppUpdateManagerFactory.create(context)
    }
//    @Binds
//    abstract fun provideApi(api: USSDController): USSDApi
}