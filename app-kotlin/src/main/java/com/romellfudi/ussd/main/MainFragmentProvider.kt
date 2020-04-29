/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main

import com.romellfudi.ussd.main.view.MainFragmentView
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by jyotidubey on 15/01/18.
 */
@Module
abstract class MainFragmentProvider {

    @ContributesAndroidInjector(modules = [MainFragmentModule::class])
    internal abstract fun provideMainFragmentView(): MainFragmentView
}