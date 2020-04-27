/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility

import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.romellfudi.ussd.accessibility.interactor.MainInteractor
import com.romellfudi.ussd.accessibility.interactor.MainMVPInteractor
import com.romellfudi.ussd.accessibility.presenter.MainMVPPresenter
import com.romellfudi.ussd.accessibility.presenter.MainPresenter
import com.romellfudi.ussd.accessibility.view.MainMVPView
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
    internal fun provideAppUpdateManager(context: Context): AppUpdateManager =
            AppUpdateManagerFactory.create(context)

    @Provides
    fun provideMainInteractor(mainInteractor: MainInteractor): MainMVPInteractor = mainInteractor

    @Provides
    internal fun provideMainPresenter(mainPresenter: MainPresenter<MainMVPView, MainMVPInteractor>)
            : MainMVPPresenter<MainMVPView, MainMVPInteractor> = mainPresenter

}