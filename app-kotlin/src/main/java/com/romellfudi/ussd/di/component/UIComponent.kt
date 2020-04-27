/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.component

import com.romellfudi.ussd.di.ActivityQualifier
import com.romellfudi.ussd.di.module.ActivityModule
import com.romellfudi.ussd.main.MainActivity
import dagger.Subcomponent

/**
 * @autor Romell Domínguez
 * @date 2020-04-20
 * @version 1.0
 */
@ActivityQualifier
@Subcomponent(modules = [ActivityModule::class])
interface UIComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UIComponent
    }

    fun inject(activity: MainActivity)
}