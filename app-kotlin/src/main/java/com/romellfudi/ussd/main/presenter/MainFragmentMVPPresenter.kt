/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.presenter

import androidx.lifecycle.LifecycleOwner
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.view.MainFragmentMVPView

interface MainFragmentMVPPresenter <V : MainFragmentMVPView, I : MainFragmentMVPInteractor>{
    fun attachObserves(lifecycleOwner: LifecycleOwner)
    fun call()
    fun callOverlay()
    fun callSplashOverlay()
//    fun onDetach()
//    fun onAttach(view: V?)
}