/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.presenter

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.romellfudi.ussd.main.entity.Response
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
@Inject internal constructor(var view: V?, var interator: I?) : MainFragmentMVPPresenter<V, I> {

    override fun attachObserves(lifecycleOwner: LifecycleOwner) {
        interator!!.getResponse().observe(lifecycleOwner, Observer {
            view?.setResult(it.result)
        })
    }

    override fun call() {
        val value = Response()
        view!!.ussdApi.callUSSDInvoke(view!!.ussdNumber, map,
                object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        value.result += "\n-\n$message"
                        view!!.ussdApi.send("1") {
                            value.result += "\n-\n$it"
                            view!!.ussdApi.send("1") {
                                value.result += "\n-\n$it"
                            }
                        }
                    }

                    override fun over(message: String) {
                        value.result += "\n-\n$message"
                        interator!!.getResponse().postValue(value)
                    }
                })
    }

    override fun callOverlay() {
        val value = Response()
        if (view!!.hasAllowOverlay) {
            view?.showOverlay()
            view!!.ussdApi.callUSSDOverlayInvoke(view!!.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            value.result += "\n-\n$message"
                            view!!.ussdApi.send("1") {
                                value.result += "\n-\n$it"
                                view!!.ussdApi.send("1") {
                                    value.result += "\n-\n$it"
                                    view?.dismissOverlay()
                                }
                            }
                        }

                        override fun over(message: String) {
                            value.result += "\n-\n$message"
                            interator!!.getResponse().postValue(value)
                            view?.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay() {
        val value = Response()
        if (view?.hasAllowOverlay!!) {
            view?.showSplashOverlay()
            view!!.ussdApi.callUSSDOverlayInvoke(view!!.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            value.result += "\n-\n$message"
                            view!!.ussdApi.send("1") {
                                value.result += "\n-\n$it"
                                view!!.ussdApi.send("1") {
                                    value.result += "\n-\n$it"
                                    view?.dismissOverlay()
                                }
                            }
                        }

                        override fun over(message: String) {
                            value.result += "\n-\n$message"
                            interator!!.getResponse().postValue(value)
                            view?.dismissOverlay()
                        }
                    })
        }
    }

//    override fun onAttach(view: V?) {
//        this.view = view
//    }

//    override fun onDetach() {
//        view = null
//        interator = null
//    }

}