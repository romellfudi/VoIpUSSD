/*
 * Copyright (c) 2021. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * portfolio.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.di

import android.app.Activity
import android.widget.ImageView
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.rbddevs.splashy.Splashy
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.BuildConfig
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.interactor.MainInteractor
import com.romellfudi.ussd.accessibility.interactor.MainMVPInteractor
import com.romellfudi.ussd.accessibility.presenter.MainMVPPresenter
import com.romellfudi.ussd.accessibility.presenter.MainPresenter
import com.romellfudi.ussd.accessibility.view.MainMVPView
import org.koin.dsl.module

/**
 * Accessibility module.
 *
 * @author Romell Domínguez
 * @version 1.12.a 27/09/2018
 * @since 1.12.a
 */
val accessibilityModule = module {

    single { (act: Activity) -> PermissionService(act) }

    factory { AppUpdateManagerFactory.create(get()) }

    single { (act: Activity) ->
        Splashy(act)
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

    single<MainMVPInteractor> { MainInteractor(get()) }
    single<MainMVPPresenter<MainMVPView, MainMVPInteractor>> { MainPresenter(get()) }


}
