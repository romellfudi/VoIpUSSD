/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.view

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
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.presenter.MainFragmentMVPPresenter
import com.romellfudi.ussdlibrary.OverlayShowingService
import com.romellfudi.ussdlibrary.SplashLoadingService
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import dagger.android.support.AndroidSupportInjection
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

class MainFragmentView : Fragment(), MainFragmentMVPView {

    override val ussdNumber: String
        get() = phone?.text.toString().trim { it <= ' ' }

    override val hasAllowOverlay: Boolean
        get() = USSDController.verifyOverLay(activity!!)

    private var overlay: Intent? = null

    @Inject
    override lateinit var ussdApi: USSDApi

    @Inject
    lateinit var permissionService: PermissionService

    @Inject
    lateinit var mainFragmentMVPPresenter: MainFragmentMVPPresenter<MainFragmentMVPView, MainFragmentMVPInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.content_op1, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        mainFragmentMVPPresenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        mainFragmentMVPPresenter.attachObserves(this)
        call.setOnClickListener { mainFragmentMVPPresenter.call() }
        call_overlay.setOnClickListener { mainFragmentMVPPresenter.callOverlay() }
        call_overlay_splash.setOnClickListener { mainFragmentMVPPresenter.callSplashOverlay() }
        accessibility.setOnClickListener { USSDController.verifyAccesibilityAccess(activity!!) }
        permissionService.request(callback)
    }

    override fun setResult(data: String) {
        result.text = data
    }

    override fun showOverlay() {
        Log.d("APP", "START OVERLAY DIALOG")
        overlay = Intent(activity, OverlayShowingService::class.java)
        overlay!!.putExtra(OverlayShowingService.EXTRA, "PROCESANDO")
        activity!!.startService(overlay)
    }

    override fun showSplashOverlay() {
        Log.d("APP", "START OVERLAY DIALOG")
        overlay = Intent(activity, SplashLoadingService::class.java)
        activity!!.startService(overlay)
    }

    override fun dismissOverlay() {
        activity!!.stopService(overlay)
        overlay = null
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


//    override fun onDestroyView() {
//        mainFragmentMVPPresenter.onDetach()
//        super.onDestroyView()
//    }
}