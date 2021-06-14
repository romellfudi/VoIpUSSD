package com.romellfudi.ussd.screen

import com.agoda.kakao.common.views.KView
import com.kaspersky.kaspresso.screens.KScreen
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.view.SplashActivity

object SplashScreen : KScreen<SplashScreen>() {

    override val layoutId: Int = R.layout.activity_splash
    override val viewClass: Class<*> = SplashActivity::class.java

    val logo = KView { withId(R.id.logo) }
    val lottie = KView { withId(R.id.lottie) }
}
