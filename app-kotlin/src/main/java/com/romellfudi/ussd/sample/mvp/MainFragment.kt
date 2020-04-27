/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.sample.mvp

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
import com.romellfudi.ussd.di.component.MainFragmentComponent
import com.romellfudi.ussd.di.module.MainFragmentModule
import com.romellfudi.ussdlibrary.OverlayShowingService
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

class MainFragment : Fragment(), MainContract.MainView {

    override var ussdNumber: String
        get() = phone.text.toString().trim { it <= ' ' }
        set(_) = Unit

    override var accessability: Boolean
        get() = USSDController.verifyAccesibilityAccess(activity!!)
        set(_) = Unit

    private var svc: Intent? = null

    @Inject
    override lateinit var ussdApi: USSDApi

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var permissionService: PermissionService

    private val mainFragmentComponent: MainFragmentComponent
            by lazy(DaggerMainFragmentComponent.builder()
            .mainFragmentModule(MainFragmentModule(this))::build)

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        mainFragmentComponent.inject(this)
        super.onCreate(savedInstanceState)
        permissionService.request(callback)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.content_op1, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        call.setOnClickListener { mainPresenter.call() }
        call_overlay.setOnClickListener { mainPresenter.callOverlay() }
        call_overlay_splash.setOnClickListener { mainPresenter.callSplashOverlay() }
        accessibility.setOnClickListener { accessability }
    }

    override fun appendResult(string: String) = result.append(string)

    override fun setResult(data: String) {
        result.text = data
    }

    override fun showOverlay() {
        Log.d("APP", "START OVERLAY DIALOG")
        svc = Intent(activity, OverlayShowingService::class.java)
        svc!!.putExtra(OverlayShowingService.EXTRA, "PROCESANDO")
        activity!!.startService(svc)
    }

    override fun dismissOverlay() {
        activity!!.stopService(svc)
        Log.d("APP", "STOP OVERLAY DIALOG")
        Log.d("APP", "successful")
    }

    private val callback = object : PermissionService.Callback() {
        override fun onResponse(refusePermissions: ArrayList<String>?) {
            Toast.makeText(context, getString(R.string.refuse_permissions), Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) =
            callback.handler(permissions, grantResults)
}