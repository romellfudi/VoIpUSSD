/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

/**
 * BoostTag E.I.R.L. All Copyright Reserved
 * www.boosttag.com
 */
package com.romellfudi.ussdlibrary

import android.app.Activity
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.view.accessibility.AccessibilityEvent
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.powermock.core.classloader.annotations.PrepareForTest
import java.util.*

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
@PrepareForTest(Uri::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class USSDApiTestKotlin {

    @Mock
    internal val activity: Activity = mock()

    @InjectMockKs
    internal var ussdController = USSDController.getInstance(activity)

    @InjectMockKs
    internal var ussdService = USSDServiceKT()

    @MockK
    lateinit var applicationInfo: ApplicationInfo

    @MockK
    lateinit var callbackInvoke: USSDController.CallbackInvoke

    @MockK
    lateinit var uri: Uri

    @MockK
    lateinit var accessibilityEvent: AccessibilityEvent

    val stringSlot = slot<String>()

    @Mock
    internal val ussdInterface: USSDInterface = mock()

    companion object {
        var i = -1
        var j = 0
        val MESSAGE = listOf(listOf("waiting", "problem UUID"),
                listOf("loading"),
                listOf("waiting", "message", "message", "message", "message", "Final Close dialog"))
        val map = HashMap<String, HashSet<String>>().apply {
            this["KEY_LOGIN"] = HashSet(listOf("espere", "waiting", "loading", "esperando"))
            this["KEY_ERROR"] = HashSet(listOf("problema", "problem", "error", "null"))
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val texts = object : ArrayList<CharSequence>() {}
        i = -1
        whenever(activity.startActivity(any())).thenAnswer { load(texts) }
        whenever(ussdInterface.sendData(any())).thenAnswer { load(texts) }
        every { accessibilityEvent.eventType } returns 0
        every { accessibilityEvent.packageName } returns "ussd.test"
        every { accessibilityEvent.eventTime } returns 1L
        every { accessibilityEvent.className } returns "amigo.app.AmigoAlertDialog"
        mockkObject(USSDController)
        mockkStatic(Uri::class)
        every { USSDController.verifyAccesibilityAccess(any()) } returns true
        every { USSDController.verifyOverLay(any()) } returns true
        every { Uri.decode(any()) } returns ""
        every { Uri.parse(any()) } returns uri
    }

    private fun load(texts: ArrayList<CharSequence>) {
        i++
        if (texts.isNotEmpty())
            texts.removeAt(0)
        texts.add(MESSAGE[j][i])
        every { accessibilityEvent.text } returns texts
        ussdService.onAccessibilityEvent(accessibilityEvent)
    }

    @Test
    fun verifyAccesibilityAccessTest() {
        j = -1
        `when`(activity.applicationInfo).thenReturn(applicationInfo)
        `when`(activity.getSystemService(any())).thenReturn(null)
        applicationInfo.nonLocalizedLabel = javaClass.getPackage().toString()
        USSDController.verifyAccesibilityAccess(activity)
    }

    @Test
    fun callUSSDInvokeTest() {
        j = 0
        every { accessibilityEvent.source } returns null

        ussdController.callUSSDInvoke("*1#", map, callbackInvoke)
        verify { callbackInvoke.over(capture(stringSlot)) }
        assertThat(stringSlot.captured, `is`(equalTo("waiting")))

        ussdController.callUSSDInvoke("*1#", map, callbackInvoke)
        verify { callbackInvoke.over(capture(stringSlot)) }
        assertThat(stringSlot.captured, `is`(equalTo("problem UUID")))
    }

    @Test
    fun callUSSDLoginWithNotInputText() {
        j = 1
        every { USSDServiceKT.notInputText(accessibilityEvent) } returns true
        every { accessibilityEvent.source } returns null

        ussdController.callUSSDInvoke("*1#", map, callbackInvoke)
        verify { callbackInvoke.over(capture(stringSlot)) }
        assertThat(stringSlot.captured, `is`(equalTo("loading")))
    }

}