/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.component

import com.romellfudi.ussd.sample.UIComponent
import dagger.Module

@Module(
        subcomponents = [
            UIComponent::class
        ]
)
class AppSubcomponents
