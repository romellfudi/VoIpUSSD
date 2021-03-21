/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility

import android.content.Context
import android.widget.ImageView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.rbddevs.splashy.Splashy
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.interactor.MainInteractor
import com.romellfudi.ussd.accessibility.interactor.MainMVPInteractor
import com.romellfudi.ussd.accessibility.presenter.MainMVPPresenter
import com.romellfudi.ussd.accessibility.presenter.MainPresenter
import com.romellfudi.ussd.accessibility.view.MainActivity
import com.romellfudi.ussd.accessibility.view.MainMVPView
import com.romellfudi.ussdlibrary.BuildConfig
import dagger.Module
import dagger.Provides

/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
@Module
class MainModule {

    @Provides
    fun providePermissionService(mainActivity: MainActivity) =
            PermissionService(mainActivity)

    @Provides
    internal fun provideAppUpdateManager(context: Context): AppUpdateManager =
            AppUpdateManagerFactory.create(context)

    @Provides
    fun provideMainInteractor(mainInteractor: MainInteractor): MainMVPInteractor = mainInteractor

    @Provides
    internal fun provideMainPresenter(mainPresenter: MainPresenter<MainMVPView, MainMVPInteractor>)
            : MainMVPPresenter<MainMVPView, MainMVPInteractor> = mainPresenter

    @Provides
    internal fun provideSplashy(mainActivity: MainActivity): Splashy = Splashy(mainActivity)
            .setLogo(R.drawable.combine)
            .setLogoScaleType(ImageView.ScaleType.FIT_CENTER)
            .setAnimation(Splashy.Animation.GROW_LOGO_FROM_CENTER, 500)
            .setTitle(R.string.app_tittle)
            .setTitleColor(R.color.black)
            .setSubTitle("Version  " + BuildConfig.VERSION_NAME)
            .setProgressColor(R.color.black)
            .setBackgroundResource(R.color.splash)
            .setClickToHide(false)
            .setFullScreen(true)
            .setTime(1500)
            .showProgress(true)

}