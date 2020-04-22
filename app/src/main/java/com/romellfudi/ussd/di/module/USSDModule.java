/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.module;

import android.content.Context;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-20
 */
@Module
public class USSDModule {

    private Context context;

    public USSDModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public USSDApi provideUSSDApi() {
        return USSDController.getInstance(context);
    }


}
