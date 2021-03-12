/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.presenter

import android.content.Context
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.view.MainFragmentMVPView
import com.romellfudi.ussdlibrary.USSDController
import javax.inject.Inject

/**
 * @autor Romell Domínguez
 * @date 2020-04-26
 * @version 1.0
 */
val map = hashMapOf(
    "KEY_LOGIN" to listOf("espere", "waiting", "loading", "esperando"),
    "KEY_ERROR" to listOf("problema", "problem", "error", "null"))

class MainFragmentPresenter<V : MainFragmentMVPView, I : MainFragmentMVPInteractor>
@Inject internal constructor(var view: V, var interator: I) : MainFragmentMVPPresenter<V, I> {

    override fun call(context: Context) {
        var result = ""
        view.ussdApi.callUSSDInvoke(context,view.ussdNumber, map,
                object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        result += "\n-\n$message"
                        view.ussdApi.send("1") {
                            result += "\n-\n$it"
                            view.ussdApi.send("2") {
                                result += "\n-\n$it"
                                view.ussdApi.send("1") {
                                    result += "\n-\n$it"
                                }
                            }
                        }
                    }

                    override fun over(message: String) {
                        result += "\n-\n$message"
                        view.showResult(result)
                    }
                })
    }

    override fun callOverlay(context: Context) {
        var result = ""
        if (view.hasAllowOverlay) {
            view.showOverlay()
            view.ussdApi.callUSSDOverlayInvoke(context,view.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            result += "\n-\n$message"
                            view.ussdApi.send("1") {
                                result += "\n-\n$it"
                                view.ussdApi.send("2") {
                                    result += "\n-\n$it"
                                    view.ussdApi.send("1") {
                                        result += "\n-\n$it"
//                                        view.dismissOverlay()
                                    }
                                }
                            }
                        }

                        override fun over(message: String) {
                            result += "\n-\n$message"
                            view.showResult(result)
                            view.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay(context: Context) {
        var result = ""
        if (view.hasAllowOverlay) {
            view.showSplashOverlay()
            view.ussdApi.callUSSDOverlayInvoke(context,view.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            result += "\n-\n$message"
                            view.ussdApi.send("1") {
                                result += "\n-\n$it"
                                view.ussdApi.send("2") {
                                    result += "\n-\n$it"
                                    view.ussdApi.send("1") {
                                        result += "\n-\n$it"
//                                        view.dismissOverlay()
                                    }
                                }
                            }
                        }

                        override fun over(message: String) {
                            result += "\n-\n$message"
                            view.showResult(result)
                            view.dismissOverlay()
                        }
                    })
        }
    }

}