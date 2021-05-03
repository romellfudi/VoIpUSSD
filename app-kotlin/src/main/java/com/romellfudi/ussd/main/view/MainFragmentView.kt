/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.romellfudi.ussd.R
import com.romellfudi.ussd.databinding.CallFragmentBinding
import com.romellfudi.ussd.main.dismissIntent
import com.romellfudi.ussd.main.entity.CallViewModel
import com.romellfudi.ussd.main.goService
import com.romellfudi.ussd.main.interactor.MainFragmentMVPInteractor
import com.romellfudi.ussd.main.presenter.MainFragmentMVPPresenter
import com.romellfudi.ussd.main.statehood.UssdState
import com.romellfudi.ussdlibrary.OverlayShowingService
import com.romellfudi.ussdlibrary.SplashLoadingService
import com.romellfudi.ussdlibrary.USSDApi
import com.romellfudi.ussdlibrary.USSDController
import kotlinx.android.synthetic.main.call_fragment.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import timber.log.Timber

/**
 * Use Case for Test Windows
 *
 * @author Romell Domínguez
 * @version 1.12.a 27/09/2018
 * @since 1.12.a
 */

class MainFragmentView : Fragment(), MainFragmentMVPView, KoinComponent {

    private val callViewModel: CallViewModel by viewModel()

    override val ussdNumber: String
        get() = phone?.text.toString().trim { it <= ' ' }

    override val hasAllowOverlay: Boolean
        get() = USSDController.verifyOverLay(requireContext())

    override val ussdApi: USSDApi by inject()

    private val mainFragmentMVPPresenter: MainFragmentMVPPresenter<MainFragmentMVPView, MainFragmentMVPInteractor>
            by inject { parametersOf(this@MainFragmentView) }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? =
            CallFragmentBinding.inflate(inflater, container, false).apply {
                lifecycleOwner = viewLifecycleOwner
                viewModel = callViewModel
                mainFragment = this@MainFragmentView
            }.run { root }

    override fun dialUp() {
        lifecycleScope.launch {
            if (callViewModel.hasNoFlavorSet())
                callViewModel.setDialUpType(getString(R.string.normal))
            activity?.let {
                if (USSDController.verifyAccesibilityAccess(it)) {
                    when (callViewModel.dialUpType.value) {
                        getString(R.string.custom) -> mainFragmentMVPPresenter.callOverlay(it)
                        getString(R.string.splash) -> mainFragmentMVPPresenter.callSplashOverlay(it)
                        else -> mainFragmentMVPPresenter.call(it)
                    }
                }
            }
        }
    }

    override fun showOverlay() {
        Timber.i("START OVERLAY DIALOG")
        goService<OverlayShowingService>(hashMapOf(OverlayShowingService.EXTRA to "PROCESANDO"))
        Handler().postDelayed(::dismissOverlay, 12000)
    }

    override fun showSplashOverlay() {
        Timber.i("START OVERLAY DIALOG")
        goService<SplashLoadingService>()
    }

    override fun showResult(result: String) =
            callViewModel.result.postValue(result)

    override fun dismissOverlay() {
        dismissIntent()
        Timber.i("TRY TO STOP OVERLAY DIALOG")
    }

    override fun observeUssdState(result: UssdState) {
        Timber.i("UssdState onGoing: $result")
        val log = when (result) {
            is UssdState.Successful -> 0
            is UssdState.Error -> result.errorMessage
            is UssdState.Progress -> result.progress
        }
        Timber.i("UssdState log string: $log")
    }

    override fun onDestroy() {
        dismissOverlay()
        super.onDestroy()
    }
}