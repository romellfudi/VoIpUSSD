/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.accessibility.presenter

import com.romellfudi.ussd.accessibility.interactor.MainMVPInteractor
import com.romellfudi.ussd.accessibility.view.MainMVPView
import javax.inject.Inject

/**
 * @autor Romell Domínguez
 * @date 2020-04-26
 * @version 1.0
 */

class MainPresenter<V : MainMVPView, I : MainMVPInteractor>
        @Inject internal constructor(var interator: I?) : MainMVPPresenter<V, I> {


}