/*
 * Copyright (c) 2021. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * portfolio.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.di

import android.app.Activity
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.romellfudi.permission.PermissionService
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

    single { PermissionService }

    factory { AppUpdateManagerFactory.create(get()) }

    single<MainMVPInteractor> { MainInteractor(get()) }
    single<MainMVPPresenter<MainMVPView, MainMVPInteractor>> { MainPresenter(get()) }


}
