package com.romellfudi.ussd.screen

import com.agoda.kakao.edit.KEditText
import com.agoda.kakao.scroll.KScrollView
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.kaspersky.components.kautomator.component.scroll.UiScrollView
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.kaspresso.screens.KScreen
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.view.MainActivity

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main_menu
    override val viewClass: Class<*> = MainActivity::class.java

    val dialUpButton = KButton { withId(R.id.accessibility) }
    val phone = KEditText { withId(R.id.phone) }
    val result = KTextView { withId(R.id.result) }
    val call_scroll = KScrollView { withId(R.id.call_scroll) }
}
