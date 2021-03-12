/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.presenter

import android.content.Context
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.view.MainFragmentMVPView

interface MainFragmentMVPPresenter<V : MainFragmentMVPView, I : MainFragmentMVPInteractor> {
    fun call(context: Context)
    fun callOverlay(context: Context)
    fun callSplashOverlay(context: Context)
}