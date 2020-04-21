package com.romellfudi.ussd.di

import com.romellfudi.ussd.sample.UIComponent
import dagger.Module

@Module(
        subcomponents = [
            UIComponent::class
        ]
)
class AppSubcomponents
