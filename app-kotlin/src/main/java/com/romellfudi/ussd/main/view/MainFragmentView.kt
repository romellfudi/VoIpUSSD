/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.romellfudi.ussd.R
import com.romellfudi.ussd.databinding.ContentOp1Binding
import com.romellfudi.ussd.main.entity.CallViewModel
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.presenter.MainFragmentMVPPresenter
import com.romellfudi.ussdlibrary.OverlayShowingService
import com.romellfudi.ussdlibrary.SplashLoadingService
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.content_op1.*
import javax.inject.Inject

/**
 * Use Case for Test Windows
 *
 * @author Romell Domínguez
 * @version 1.1.b 27/09/2018
 * @since 1.0.a
 */

class MainFragmentView : Fragment(), MainFragmentMVPView {

    private val callViewModel: CallViewModel by activityViewModels()

    override val ussdNumber: String
        get() = phone?.text.toString().trim { it <= ' ' }

    override val hasAllowOverlay: Boolean?
        get() = activity?.let { USSDController.verifyOverLay(it) }

    private var overlay: Intent? = null

    @Inject
    override lateinit var ussdApi: USSDApi

    private var binding: ContentOp1Binding? = null

    @Inject
    lateinit var mainFragmentMVPPresenter: MainFragmentMVPPresenter<MainFragmentMVPView, MainFragmentMVPInteractor>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mainFragment = ContentOp1Binding.inflate(inflater, container, false)
        binding = mainFragment
        return mainFragment.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = callViewModel
            mainFragment = this@MainFragmentView
        }
    }

    override fun dialUp() {
        if (callViewModel.hasNoFlavorSet())
            callViewModel.setDialUpType(getString(R.string.normal))
        activity?.let {
            if (USSDController.verifyAccesibilityAccess(it)) {
                when (callViewModel.dialUpType.value) {
                    getString(R.string.custom) -> mainFragmentMVPPresenter.callOverlay()
                    getString(R.string.splash) -> mainFragmentMVPPresenter.callSplashOverlay()
                    else -> mainFragmentMVPPresenter.call()
                }
            }
        }
    }

    override fun showOverlay() {
        Log.d("APP", "START OVERLAY DIALOG")
        activity?.run {
            overlay = Intent(this, OverlayShowingService::class.java)
            overlay?.putExtra(OverlayShowingService.EXTRA, "PROCESANDO")
            startService(overlay)
        }
    }

    override fun showSplashOverlay() {
        Log.d("APP", "START OVERLAY DIALOG")
        activity?.run {
            overlay = Intent(activity, SplashLoadingService::class.java)
            startService(overlay)
        }
    }

    override fun showResult(result: String) =
        callViewModel.result.postValue(result)

    override fun dismissOverlay() {
        activity?.stopService(overlay)
        overlay = null
        Log.d("APP", "STOP OVERLAY DIALOG")
    }

    override fun onPause() {
        // EditText
        callViewModel.number.postValue(ussdNumber)
        super.onPause()
    }
}