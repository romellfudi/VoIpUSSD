/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */
package com.romellfudi.ussd.di.module

import androidx.fragment.app.Fragment
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.di.FragmentScope
import com.romellfudi.ussd.sample.mvp.MainContract
import com.romellfudi.ussd.sample.mvp.MainPresenter
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
class MainFragmentModule(var fragment: Fragment) {

    @Provides
    @FragmentScope
    fun providePermissionService(): PermissionService {
        return PermissionService(fragment.activity)
    }

    @Provides
    @FragmentScope
    fun provideUSSDApi(): USSDApi {
        return USSDController.getInstance(fragment.activity!!.applicationContext)
    }

    @Provides
    @FragmentScope
    fun providePresenter(): MainPresenter {
        return MainPresenter(fragment as MainContract.MainView)
    }
}