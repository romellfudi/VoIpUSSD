/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.sample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.R
import com.romellfudi.ussd.di.component.DaggerMainFragmentComponent
import com.romellfudi.ussd.di.module.MainFragmentModule
import com.romellfudi.ussdlibrary.OverlayShowingService
import com.romellfudi.ussdlibrary.SplashLoadingService
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import kotlinx.android.synthetic.main.content_op1.*
import java.util.*
import javax.inject.Inject

/**
 * Use Case for Test Windows
 *
 * @author Romell Domínguez
 * @version 1.1.b 27/09/2018
 * @since 1.0.a
 */

class MainFragment : Fragment() {

    private lateinit var map: HashMap<String, HashSet<String>>

    @Inject
    lateinit var ussdApi: USSDApi

    @Inject
    lateinit var permissionService: PermissionService

    private val callback = object : PermissionService.Callback() {
        override fun onResponse(refusePermissions: ArrayList<String>?) {
            Toast.makeText(context,
                    getString(R.string.refuse_permissions),
                    Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }

    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
//        (activity!!.application as App).appComponent.uiComponent().create().inject(this)
        DaggerMainFragmentComponent.builder()
//                .appComponent((activity!!.application as App).initializeComponent())//appComponent.uiComponent().create())
                .mainFragmentModule(MainFragmentModule(activity!!))
                .build().inject(this)

        super.onCreate(savedInstanceState)

        map = HashMap()
        map["KEY_LOGIN"] = HashSet(listOf("espere", "waiting", "loading", "esperando"))
        map["KEY_ERROR"] = HashSet(listOf("problema", "problem", "error", "null"))

        permissionService.request(callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_op1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)

        btn1.setOnClickListener {
            val phoneNumber = phone!!.text.toString().trim { it <= ' ' }
            ussdApi = USSDController.getInstance(activity!!)
            result.text = ""
            ussdApi.callUSSDInvoke(phoneNumber, map!!,
                    object : USSDController.CallbackInvoke {
                        override fun responseInvoke(message: String) {
                            Log.d("APP", message)
                            result.append("\n-\n$message")
                            // first option list - select option 1
                            ussdApi.send("1") {
                                Log.d("APP", it)
                                result.append("\n-\n$it")
                                // second option list - select option 1
                                ussdApi.send("1") {
                                    Log.d("APP", it)
                                    result.append("\n-\n$it")
                                }
                            }
//                            ussdApi.cancel()
                        }

                        override fun over(message: String) {
                            Log.d("APP", message)
                            result.append("\n-\n$message")
                        }
                    })
        }

        btn2.setOnClickListener {
            if (USSDController.verifyOverLay(activity!!)) {
                val svc = Intent(activity, OverlayShowingService::class.java)
                svc.putExtra(OverlayShowingService.EXTRA, "PROCESANDO")
                activity!!.startService(svc)
                Log.d("APP", "START OVERLAY DIALOG")
                val phoneNumber = phone!!.text.toString().trim { it <= ' ' }
                ussdApi = USSDController.getInstance(activity!!)
                result.text = ""
                ussdApi.callUSSDOverlayInvoke(phoneNumber, map!!,
                        object : USSDController.CallbackInvoke {
                            override fun responseInvoke(message: String) {
                                Log.d("APP", message)
                                result.append("\n-\n$message")
                                // first option list - select option 1
                                ussdApi.send("1") {
                                    Log.d("APP", it)
                                    result.append("\n-\n$it")
                                    // second option list - select option 1
                                    ussdApi.send("1") {
                                        Log.d("APP", it)
                                    }
                                }
//                                ussdApi.cancel()
                            }

                            override fun over(message: String) {
                                Log.d("APP", message)
                                result.append("\n-\n$message")
                                activity!!.stopService(svc)
                                Log.d("APP", "STOP OVERLAY DIALOG")
                            }
                        })
            }
        }

        btn4.setOnClickListener {
            if (USSDController.verifyOverLay(activity!!)) {
                val svc = Intent(activity, SplashLoadingService::class.java)
                activity!!.startService(svc)
                Log.d("APP", "START SPLASH DIALOG")
                val phoneNumber = phone!!.text.toString().trim { it <= ' ' }
                result.text = ""
                ussdApi.callUSSDOverlayInvoke(phoneNumber, map,
                        object : USSDController.CallbackInvoke {
                            override fun responseInvoke(message: String) {
                                Log.d("APP", message)
                                result.append("\n-\n$message")
                                // first option list - select option 1
                                ussdApi.send("1") {
                                    Log.d("APP", it)
                                    result.append("\n-\n$message")
                                    // second option list - select option 1
                                    ussdApi.send("1") {
                                        Log.d("APP", it)
                                        result.append("\n-\n$it")
                                        activity!!.stopService(svc)
                                        Log.d("APP", "STOP SPLASH DIALOG")
                                        Log.d("APP", "successful")
                                    }
                                }
//                                ussdApi.cancel()
                            }

                            override fun over(message: String) {
                                Log.d("APP", message)
                                result!!.append("\n-\n$message")
                                activity!!.stopService(svc)
                                Log.d("APP", "STOP OVERLAY DIALOG")
                            }
                        })
            }
        }

        btn3.setOnClickListener(fun(it: View) {
            USSDController.verifyAccesibilityAccess(activity!!)
        })

    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) =
            callback.handler(permissions, grantResults)

}