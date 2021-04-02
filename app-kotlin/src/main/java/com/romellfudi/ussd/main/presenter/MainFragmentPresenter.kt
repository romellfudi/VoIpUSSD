/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.presenter

import android.content.Context
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.statehood.UssdState
import com.romellfudi.ussd.main.view.MainFragmentMVPView
import com.romellfudi.ussdlibrary.USSDController

/**
 * @autor Romell Domínguez
 * @date 2020-04-26
 * @version 1.0
 */
val map = hashMapOf(
        "KEY_LOGIN" to listOf("espere", "waiting", "loading", "esperando"),
        "KEY_ERROR" to listOf("problema", "problem", "error", "null"))

class MainFragmentPresenter<V : MainFragmentMVPView, I : MainFragmentMVPInteractor>
internal constructor(var view: V, var interator: I) : MainFragmentMVPPresenter<V, I> {

    override fun call(context: Context) {
        var result = ""
        var finish = false
        view.observeUssdState(UssdState.Progress(0))
        view.ussdApi.callUSSDInvoke(context, view.ussdNumber, map,
                object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        result += "\n-\n$message"
                        view.observeUssdState(UssdState.Progress(25))
                        view.ussdApi.send("1") {
                            view.observeUssdState(UssdState.Progress(50))
                            result += "\n-\n$it"
                            view.ussdApi.send("2") {
                                view.observeUssdState(UssdState.Progress(75))
                                result += "\n-\n$it"
                                finish = true
                                view.ussdApi.send("1") {}
                            }
                        }
                    }

                    override fun over(message: String) {
                        result += "\n-\n$message"
                        when {
                            finish -> view.observeUssdState(UssdState.Successful)
                            else -> view.observeUssdState(UssdState.Error())
                        }
                        view.showResult(result)
                    }
                })
    }

    override fun callOverlay(context: Context) {
        var result = ""
        var finish = false
        view.observeUssdState(UssdState.Progress(0))
        if (view.hasAllowOverlay) {
            view.showOverlay()
            view.ussdApi.callUSSDOverlayInvoke(context, view.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            view.observeUssdState(UssdState.Progress(25))
                            result += "\n-\n$message"
                            view.ussdApi.send("1") {
                                view.observeUssdState(UssdState.Progress(50))
                                result += "\n-\n$it"
                                view.ussdApi.send("2") {
                                    view.observeUssdState(UssdState.Progress(75))
                                    result += "\n-\n$it"
                                    finish = true
                                    view.ussdApi.send("1") { }
                                }
                            }
                        }

                        override fun over(message: String) {
                            result += "\n-\n$message"
                            when {
                                finish -> view.observeUssdState(UssdState.Successful)
                                else -> view.observeUssdState(UssdState.Error())
                            }
                            view.showResult(result)
                            view.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay(context: Context) {
        var result = ""
        var finish = false
        view.observeUssdState(UssdState.Progress(0))
        if (view.hasAllowOverlay) {
            view.showSplashOverlay()
            view.ussdApi.callUSSDOverlayInvoke(context, view.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            view.observeUssdState(UssdState.Progress(25))
                            result += "\n-\n$message"
                            view.ussdApi.send("1") {
                                view.observeUssdState(UssdState.Progress(50))
                                result += "\n-\n$it"
                                view.ussdApi.send("2") {
                                    view.observeUssdState(UssdState.Progress(75))
                                    result += "\n-\n$it"
                                    finish = true
                                    view.ussdApi.send("1") { }
                                }
                            }
                        }

                        override fun over(message: String) {
                            result += "\n-\n$message"
                            if (finish) view.observeUssdState(UssdState.Successful)
                            else view.observeUssdState(UssdState.Error())
                            view.showResult(result)
                            view.dismissOverlay()
                        }
                    })
        }
    }

}