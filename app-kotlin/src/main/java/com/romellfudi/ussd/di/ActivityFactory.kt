/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di

import com.romellfudi.ussd.accessibility.MainModule
import com.romellfudi.ussd.accessibility.view.MainActivity
import com.romellfudi.ussd.main.MainFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
@Module
abstract class ActivityFactory {

    @ContributesAndroidInjector(modules = [(MainModule::class), (MainFragmentProvider::class)])
    abstract fun bindMainActivity(): MainActivity

}