/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */
package com.romellfudi.ussd.di.component

import com.romellfudi.ussd.di.FragmentScope
import com.romellfudi.ussd.di.module.MainFragmentModule
import com.romellfudi.ussd.main.view.MainMVPFragment
import dagger.Component

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-22
 */
@FragmentScope
@Component(modules = [MainFragmentModule::class])
interface MainFragmentComponent {

    fun inject(fragment: MainMVPFragment)
}