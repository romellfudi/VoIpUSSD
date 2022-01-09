package com.romellfudi.ussd.screen

import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.scroll.KScrollView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import com.kaspersky.kaspresso.screens.KScreen
import com.romellfudi.ussd.R
import com.romellfudi.ussd.accessibility.view.MainActivity

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_main_menu
    override val viewClass: Class<*> = MainActivity::class.java

    val dialUpButton = KButton { withId(R.id.accessibility) }
    val radioSplash = KButton { withId(R.id.splash)}
    val phone = KEditText { withId(R.id.phone) }
    val result = KTextView { withId(R.id.result) }
    val call_scroll = KScrollView { withId(R.id.call_scroll) }
}
