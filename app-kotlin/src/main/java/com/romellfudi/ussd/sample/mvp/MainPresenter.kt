package com.romellfudi.ussd.sample.mvp

import android.util.Log
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

class MainPresenter(var mainView: MainContract.MainView) : MainContract.MainActions {

    override fun call() {
        mainView.setResult("")
        mainView.ussdApi.callUSSDInvoke(mainView.ussdNumber, map,
                object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        Log.d("APP", message)
                        mainView.appendResult("\n-\n$message")
                        // first option list - select option 1
                        mainView.ussdApi.send("1") {
                            Log.d("APP", it)
                            mainView.appendResult("\n-\n$it")
                            // second option list - select option 1
                            mainView.ussdApi.send("1") {
                                Log.d("APP", it)
                                mainView.appendResult("\n-\n$it")
                            }
                        }
//                            mainView.ussdApi.cancel()
                    }

                    override fun over(message: String) {
                        Log.d("APP", message)
                        mainView.appendResult("\n-\n$message")
                    }
                })
    }

    override fun callOverlay() {
        if (mainView.accessability) {
            mainView.showOverlay()
            mainView.setResult("")
            mainView.ussdApi.callUSSDOverlayInvoke(mainView.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            mainView.appendResult("\n-\n$message")
                            // first option list - select option 1
                            mainView.ussdApi.send("1") {
                                Log.d("APP", it)
                                mainView.appendResult("\n-\n$it")
                                // second option list - select option 1
                                mainView.ussdApi.send("1") {
                                    Log.d("APP", it)
                                    mainView.dismissOverlay()
                                }
                            }
//                                mainView.ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            mainView.appendResult("\n-\n$message")
                            mainView.dismissOverlay()
                        }
                    })
        }

    }

    override fun callSplashOverlay() {
        if (mainView.accessability ) {
            mainView.showOverlay()
            mainView.setResult("")
            mainView.ussdApi.callUSSDOverlayInvoke(mainView.ussdNumber, map,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            mainView.appendResult("\n-\n$message")
                            // first option list - select option 1
                            mainView.ussdApi.send("1") {
                                Log.d("APP", it)
                                mainView.appendResult("\n-\n$message")
                                // second option list - select option 1
                                mainView.ussdApi.send("1") {
                                    Log.d("APP", it)
                                    mainView.appendResult("\n-\n$it")
                                    mainView.dismissOverlay()
                                }
                            }
//                                ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            mainView.appendResult("\n-\n$message")
                            mainView.dismissOverlay()
                        }
                    })
        }
    }

}