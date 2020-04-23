/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */
package com.romellfudi.ussd.di.module

import android.app.Activity
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.di.FragmentScope
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import dagger.Module
import dagger.Provides

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-22
 */
@Module
class MainFragmentModule(var activity: Activity) {

    @Provides
    @FragmentScope
    fun providePermissionService(): PermissionService {
        return PermissionService(activity)
    }

    @Provides
    @FragmentScope
    fun provideUSSDApi(): USSDApi {
        return USSDController.getInstance(activity.applicationContext)
    }
}