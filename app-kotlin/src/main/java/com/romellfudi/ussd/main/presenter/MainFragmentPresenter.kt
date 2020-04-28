package com.romellfudi.ussd.main.presenter

import android.util.Log
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.view.MainFragmentMVPView
import com.romellfudi.ussdlibrary.USSDController
import java.util.*
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

    override fun call() {
        view?.setResult("")
        view!!.ussdApi.callUSSDInvoke(view!!.ussdNumber, map,
                object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        Log.d("APP", message)
                        view?.appendResult("\n-\n$message")
                        // first option list - select option 1
                        view!!.ussdApi.send("1") {
                            Log.d("APP", it)
                            view?.appendResult("\n-\n$it")
                            // second option list - select option 1
                            view!!.ussdApi.send("1") {
                                Log.d("APP", it)
                                view?.appendResult("\n-\n$it")
                            }
                        }
//                            mainView.ussdApi.cancel()
                    }

                    override fun over(message: String) {
                        Log.d("APP", message)
                        view?.appendResult("\n-\n$message")
                    }
                })
    }

    override fun callOverlay() {
        if (view!!.hasAllowOverlay) {
            view?.showOverlay()
            view?.setResult("")
            view!!.ussdApi.callUSSDOverlayInvoke(view!!.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            view?.appendResult("\n-\n$message")
                            // first option list - select option 1
                            view!!.ussdApi.send("1") {
                                Log.d("APP", it)
                                view?.appendResult("\n-\n$it")
                                // second option list - select option 1
                                view!!.ussdApi.send("1") {
                                    Log.d("APP", it)
                                    view?.appendResult("\n-\n$it")
                                    view?.dismissOverlay()
                                }
                            }
//                                view?.ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            view?.appendResult("\n-\n$message")
                            view?.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay() {
        if (view?.hasAllowOverlay!!) {
            view?.showSplashOverlay()
            view?.setResult("")
            view!!.ussdApi.callUSSDOverlayInvoke(view!!.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            view?.appendResult("\n-\n$message")
                            // first option list - select option 1
                            view!!.ussdApi.send("1") {
                                Log.d("APP", it)
                                view?.appendResult("\n-\n$it")
                                // second option list - select option 1
                                view!!.ussdApi.send("1") {
                                    Log.d("APP", it)
                                    view?.appendResult("\n-\n$it")
                                    view?.dismissOverlay()
                                }
                            }
//                                view?.ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            view?.appendResult("\n-\n$message")
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