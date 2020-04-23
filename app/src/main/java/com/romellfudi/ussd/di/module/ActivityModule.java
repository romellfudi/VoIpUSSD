/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.di.module;

import android.app.Activity;
import android.content.Context;

import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.romellfudi.ussd.di.ActivityContext;
import com.romellfudi.ussd.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by janisharali on 08/12/16.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityScope
    public AppUpdateManager provideAppUpdateManager() {
        return AppUpdateManagerFactory.create(mActivity);
    }
}
