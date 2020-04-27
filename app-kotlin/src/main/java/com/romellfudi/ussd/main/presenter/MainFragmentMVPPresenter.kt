package com.romellfudi.ussd.main.presenter

import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.view.MainFragmentMVPView

interface MainFragmentMVPPresenter <V : MainFragmentMVPView, I : MainFragmentMVPInteractor>{

    fun call()
    fun callOverlay()
    fun callSplashOverlay()

    fun getView(): V
    fun onDetach()
    fun onAttach(view: V?)
}