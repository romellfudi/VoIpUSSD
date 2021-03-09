/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.presenter

import com.romellfudi.ussd.main.entity.CallViewModel
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.view.MainFragmentMVPView
import com.romellfudi.ussdlibrary.USSDController
import javax.inject.Inject

/**
 * @autor Romell Domínguez
 * @date 2020-04-26
 * @version 1.0
 */
var map = HashMap<String, HashSet<String>>().apply {
    this["KEY_LOGIN"] = HashSet(listOf("espere", "waiting", "loading", "esperando"))
    this["KEY_ERROR"] = HashSet(listOf("problema", "problem", "error", "null"))
}

class MainFragmentPresenter<V : MainFragmentMVPView, I : MainFragmentMVPInteractor>
@Inject internal constructor(var view: V, var interator: I) : MainFragmentMVPPresenter<V, I> {

    lateinit var viewModel: CallViewModel

    override fun attachObserves(callViewModel: CallViewModel) {
        viewModel = callViewModel
    }

    override fun call() {
        var result = ""
        view.ussdApi.callUSSDInvoke(view.ussdNumber, map,
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
                        viewModel.result.postValue(result)
                    }
                })
    }

    override fun callOverlay() {
        var result = ""
        if (view.hasAllowOverlay!!) {
            view.showOverlay()
            view.ussdApi.callUSSDOverlayInvoke(view.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            result += "\n-\n$message"
                            view.ussdApi.send("1") {
                                result += "\n-\n$it"
                                view.ussdApi.send("2") {
                                    result += "\n-\n$it"
                                    view.ussdApi.send("1") {
                                        result += "\n-\n$it"
                                        view.dismissOverlay()
                                    }
                                }
                            }
                        }

                        override fun over(message: String) {
                            result += "\n-\n$message"
                            viewModel.result.postValue(result)
                            view.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay() {
        var result = ""
        if (view.hasAllowOverlay!!) {
            view.showSplashOverlay()
            view.ussdApi.callUSSDOverlayInvoke(view.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            result += "\n-\n$message"
                            view.ussdApi.send("1") {
                                result += "\n-\n$it"
                                view.ussdApi.send("2") {
                                    result += "\n-\n$it"
                                    view.ussdApi.send("1") {
                                        result += "\n-\n$it"
                                        view.dismissOverlay()
                                    }
                                }
                            }
                        }

                        override fun over(message: String) {
                            result += "\n-\n$message"
                            viewModel.result.postValue(result)
                            view.dismissOverlay()
                        }
                    })
        }
    }

}