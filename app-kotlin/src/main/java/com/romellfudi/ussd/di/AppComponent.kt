package com.romellfudi.ussd.di

import android.content.Context
import com.romellfudi.ussd.sample.UIComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [USSDModule::class, AppSubcomponents::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun uiComponent(): UIComponent.Factory
}
