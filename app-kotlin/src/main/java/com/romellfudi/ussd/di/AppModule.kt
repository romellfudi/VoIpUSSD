/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context = application


}