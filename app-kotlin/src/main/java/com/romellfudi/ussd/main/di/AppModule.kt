/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.di

import android.os.Handler
import com.romellfudi.ussd.main.entity.CallViewModel
import com.romellfudi.ussd.main.interactor.MainFragmentInteractor
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.presenter.MainFragmentMVPPresenter
import com.romellfudi.ussd.main.presenter.MainFragmentPresenter
import com.romellfudi.ussd.main.view.MainFragmentMVPView
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Application module.
 *
 * @author Romell Domínguez
 * @version 1.12.a 01/03/2021
 * @since 1.0.a 27/09/2018
 */
val appModule = module {
    viewModel { CallViewModel() }
    single<USSDApi> { USSDController }
    single<MainFragmentMVPPresenter<MainFragmentMVPView, MainFragmentMVPInteractor>> { (view: MainFragmentMVPView) ->
        MainFragmentPresenter(view, get())
    }
    single { Handler() }
    single<MainFragmentMVPInteractor> { MainFragmentInteractor(get()) }
}
