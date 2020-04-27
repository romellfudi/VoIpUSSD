package com.romellfudi.ussd.main.presenter

import android.util.Log
import com.romellfudi.ussd.main.view.MainMVPView
import com.romellfudi.ussdlibrary.USSDController
import java.util.*

/**
 * @autor Romell Domínguez
 * @date 2020-04-26
 * @version 1.0
 */
var map = HashMap<String, HashSet<String>>().apply {
    this["KEY_LOGIN"] = HashSet(listOf("espere", "waiting", "loading", "esperando"))
    this["KEY_ERROR"] = HashSet(listOf("problema", "problem", "error", "null"))
}

class MainPresenter(var mainMVPView: MainMVPView) : MainMVPPresenter {

    override fun call() {
        mainMVPView.setResult("")
        mainMVPView.ussdApi.callUSSDInvoke(mainMVPView.ussdNumber, map,
                object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        Log.d("APP", message)
                        mainMVPView.appendResult("\n-\n$message")
                        // first option list - select option 1
                        mainMVPView.ussdApi.send("1") {
                            Log.d("APP", it)
                            mainMVPView.appendResult("\n-\n$it")
                            // second option list - select option 1
                            mainMVPView.ussdApi.send("1") {
                                Log.d("APP", it)
                                mainMVPView.appendResult("\n-\n$it")
                            }
                        }
//                            mainView.ussdApi.cancel()
                    }

                    override fun over(message: String) {
                        Log.d("APP", message)
                        mainMVPView.appendResult("\n-\n$message")
                    }
                })
    }

    override fun callOverlay() {
        if (mainMVPView.accessability) {
            mainMVPView.showOverlay()
            mainMVPView.setResult("")
            mainMVPView.ussdApi.callUSSDOverlayInvoke(mainMVPView.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            mainMVPView.appendResult("\n-\n$message")
                            // first option list - select option 1
                            mainMVPView.ussdApi.send("1") {
                                Log.d("APP", it)
                                mainMVPView.appendResult("\n-\n$it")
                                // second option list - select option 1
                                mainMVPView.ussdApi.send("1") {
                                    Log.d("APP", it)
                                    mainMVPView.dismissOverlay()
                                }
                            }
//                                mainView.ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            mainMVPView.appendResult("\n-\n$message")
                            mainMVPView.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay() {
        if (mainMVPView.accessability) {
            mainMVPView.showOverlay()
            mainMVPView.setResult("")
            mainMVPView.ussdApi.callUSSDOverlayInvoke(mainMVPView.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            mainMVPView.appendResult("\n-\n$message")
                            // first option list - select option 1
                            mainMVPView.ussdApi.send("1") {
                                Log.d("APP", it)
                                mainMVPView.appendResult("\n-\n$message")
                                // second option list - select option 1
                                mainMVPView.ussdApi.send("1") {
                                    Log.d("APP", it)
                                    mainMVPView.appendResult("\n-\n$it")
                                    mainMVPView.dismissOverlay()
                                }
                            }
//                                ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            mainMVPView.appendResult("\n-\n$message")
                            mainMVPView.dismissOverlay()
                        }
                    })
        }
    }

}