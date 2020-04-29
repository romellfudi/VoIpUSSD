/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */
package com.romellfudi.ussd.main

import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.main.interactor.MainFragmentInteractor
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.presenter.MainFragmentMVPPresenter
import com.romellfudi.ussd.main.presenter.MainFragmentPresenter
import com.romellfudi.ussd.main.view.MainFragmentMVPView
import com.romellfudi.ussd.main.view.MainFragmentView
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import dagger.Module
import dagger.Provides

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-22
 */
@Module
class MainFragmentModule {

    @Provides
    fun providePermissionService(fragment: MainFragmentView) =
            PermissionService(fragment.activity)

    @Provides
    fun provideUSSDApi(fragment: MainFragmentView): USSDApi =
            USSDController.getInstance(fragment.activity!!)

    @Provides
    internal fun provideMainFragmentView(view: MainFragmentView): MainFragmentMVPView = view

    @Provides
    internal fun provideMainFragmentInteractor(interactor: MainFragmentInteractor): MainFragmentMVPInteractor = interactor

    @Provides
    internal fun provideMainFragmentPresenter(presenter: MainFragmentPresenter<MainFragmentMVPView, MainFragmentMVPInteractor>)
            : MainFragmentMVPPresenter<MainFragmentMVPView, MainFragmentMVPInteractor> = presenter

}