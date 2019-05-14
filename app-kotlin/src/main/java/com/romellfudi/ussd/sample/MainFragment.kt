package com.romellfudi.ussd.sample

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.romellfudi.permission.PermissionService
import com.romellfudi.ussd.R
import com.romellfudi.ussdlibrary.OverlayShowingService
import com.romellfudi.ussdlibrary.SplashLoadingService
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap
import java.util.HashSet

/**
 * Use Case for Test Windows
 *
 * @author Romell Domínguez
 * @version 1.1.b 27/09/2018
 * @since 1.0.a
 */

class MainFragment : Fragment() {

    private var result: TextView? = null
    private var phone: EditText? = null
    private var btn1: Button? = null
    private var btn2: Button? = null
    private var btn3: Button? = null
    private var btn4: Button? = null
    private var map: HashMap<String, HashSet<String>>? = null
    private var ussdApi: USSDApi? = null
    private var menuActivity: MainActivity? = null

    private val callback = object : PermissionService.Callback() {
        override fun onRefuse(RefusePermissions: ArrayList<String>) {
            Toast.makeText(context,
                    getString(R.string.refuse_permissions),
                    Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }

        override fun onFinally() {
            // pass
        }
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        map = HashMap()
        map!!["KEY_LOGIN"] = HashSet(Arrays.asList("espere", "waiting", "loading", "esperando"))
        map!!["KEY_ERROR"] = HashSet(Arrays.asList("problema", "problem", "error", "null"))
        ussdApi = USSDController.getInstance(activity!!)
        menuActivity = activity as MainActivity?
        PermissionService(activity).request(
                arrayOf(permission.CALL_PHONE, permission.READ_PHONE_STATE),
                callback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.content_op1, container, false)
        result = view.findViewById<View>(R.id.result) as TextView
        phone = view.findViewById<View>(R.id.phone) as EditText
        btn1 = view.findViewById<View>(R.id.btn1) as Button
        btn2 = view.findViewById<View>(R.id.btn2) as Button
        btn3 = view.findViewById<View>(R.id.btn3) as Button
        btn4 = view.findViewById<View>(R.id.btn4) as Button
        setHasOptionsMenu(false)

        btn1!!.setOnClickListener {
            val phoneNumber = phone!!.text.toString().trim { it <= ' ' }
            ussdApi = USSDController.getInstance(activity!!)
            result!!.text = ""
            ussdApi!!.callUSSDInvoke(phoneNumber, map!!, object : USSDController.CallbackInvoke {
                override fun responseInvoke(message: String) {
                    Log.d("APP", message)
                    result!!.append("\n-\n$message")
                    // first option list - select option 1
                    ussdApi!!.send("1") {
                            Log.d("APP", it)
                            result!!.append("\n-\n$it")
                            // second option list - select option 1
                            ussdApi!!.send("1") {
                                Log.d("APP", it)
                                result!!.append("\n-\n$it")
                            }
                    }
                }

                override fun over(message: String) {
                    Log.d("APP", message)
                    result!!.append("\n-\n$message")
                }
            })
        }

        btn2!!.setOnClickListener(fun(_: View) {
            if (USSDController.verifyOverLay(activity!!)) {
                val svc = Intent(activity, OverlayShowingService::class.java)
                svc.putExtra(OverlayShowingService.EXTRA, "PROCESANDO")
                activity!!.startService(svc)
                Log.d("APP", "START OVERLAY DIALOG")
                val phoneNumber = phone!!.text.toString().trim { it <= ' ' }
                ussdApi = USSDController.getInstance(activity!!)
                result!!.text = ""
                ussdApi!!.callUSSDOverlayInvoke(phoneNumber, map!!, object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        Log.d("APP", message)
                        result!!.append("\n-\n$message")
                        // first option list - select option 1
                        ussdApi!!.send("1") {
                            Log.d("APP", it)
                            result!!.append("\n-\n$it")
                            // second option list - select option 1
                            ussdApi!!.send("1") {
                                Log.d("APP", it)
                            }
                        }
                    }

                    override fun over(message: String) {
                        Log.d("APP", message)
                        result!!.append("\n-\n$message")
                        activity!!.stopService(svc)
                        Log.d("APP", "STOP OVERLAY DIALOG")
                    }
                })
            }
        })

        btn4!!.setOnClickListener {
            if (USSDController.verifyOverLay(activity!!)) {
                val svc = Intent(activity, SplashLoadingService::class.java)
                activity!!.startService(svc)
                Log.d("APP", "START SPLASH DIALOG")
                val phoneNumber = phone!!.text.toString().trim { it <= ' ' }
                result!!.text = ""
                ussdApi!!.callUSSDOverlayInvoke(phoneNumber, map!!, object : USSDController.CallbackInvoke {
                    override fun responseInvoke(message: String) {
                        Log.d("APP", message)
                        result!!.append("\n-\n$message")
                        // first option list - select option 1
                        ussdApi!!.send("1", fun(message) {
                                Log.d("APP", message)
                                result!!.append("\n-\n$message")
                                // second option list - select option 1
                                ussdApi!!.send("1") {
                                        Log.d("APP", it)
                                        result!!.append("\n-\n$it")
                                        activity!!.stopService(svc)
                                        Log.d("APP", "STOP SPLASH DIALOG")
                                        Log.d("APP", "successful")
                                }
                        })
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

        btn3!!.setOnClickListener { USSDController.verifyAccesibilityAccess(activity!!) }

        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        callback.handler(permissions, grantResults)
    }
}