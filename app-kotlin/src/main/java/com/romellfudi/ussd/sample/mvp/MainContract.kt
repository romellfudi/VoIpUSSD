package com.romellfudi.ussd.sample.mvp

import com.romellfudi.ussdlibrary.USSDApi

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 2020-04-26
 */
interface MainContract {
    interface MainActions {
        fun call()
        fun callOverlay()
        fun callSplashOverlay()
    }

    interface MainView {
        var ussdNumber: String
        fun setResult(data: String)
        fun appendResult(string: String)
        var ussdApi: USSDApi
        var accessability: Boolean
        fun showOverlay()
        fun dismissOverlay()
    }
}