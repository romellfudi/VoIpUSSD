/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.sample

import com.romellfudi.ussd.di.ActivityScope
import dagger.Subcomponent

/**
 * @autor Romell Domínguez
 * @date 2020-04-20
 * @version 1.0
 */
@ActivityScope
@Subcomponent
interface UIComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UIComponent
    }

    fun inject(fragment: MainFragment)
}