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

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
}