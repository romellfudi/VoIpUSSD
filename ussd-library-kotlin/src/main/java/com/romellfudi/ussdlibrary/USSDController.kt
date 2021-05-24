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

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telecom.TelecomManager
import android.view.accessibility.AccessibilityManager

/**
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
@SuppressLint("StaticFieldLeak")
object USSDController : USSDInterface, USSDApi {

    const val KEY_LOGIN = "KEY_LOGIN"
    const val KEY_ERROR = "KEY_ERROR"

    private val simSlotName = arrayOf("extra_asus_dial_use_dualsim",
            "com.android.phone.extra.slot", "slot", "simslot", "sim_slot", "subscription",
            "Subscription", "phone", "com.android.phone.DialingMode", "simSlot", "slot_id",
            "simId", "simnum", "phone_type", "slotId", "slotIdx")

    lateinit var context: Context
        private set

    lateinit var map: HashMap<String, List<String>>
        private set

    lateinit var callbackInvoke: CallbackInvoke

    var callbackMessage: ((String) -> Unit)? = null

    var isRunning: Boolean? = false
    var sendType: Boolean? = false

    var ussdInterface: USSDInterface? = null

    init {
        ussdInterface = this
    }

    /**
     * Invoke a dial-up calling a ussd number
     *
     * @param ussdPhoneNumber ussd number
     * @param hashMap             Map of Login and problem messages
     * @param callbackInvoke  a callback object from return answer
     */
    override fun callUSSDInvoke(baseContext: Context, ussdPhoneNumber: String, hashMap: HashMap<String, List<String>>,
                                callbackInvoke: CallbackInvoke) {
        context = baseContext
        sendType = false
        callUSSDInvoke(context, ussdPhoneNumber, 0, hashMap, callbackInvoke)
    }

    /**
     * Invoke a dial-up calling a ussd number and
     * you had a overlay progress widget
     *
     * @param ussdPhoneNumber ussd number
     * @param hashMap         Map of Login and problem messages
     * @param callbackInvoke  a callback object from return answer
     */
    override fun callUSSDOverlayInvoke(baseContext: Context, ussdPhoneNumber: String, hashMap: HashMap<String, List<String>>,
                                       callbackInvoke: CallbackInvoke) {
        context = baseContext
        sendType = false
        callUSSDOverlayInvoke(context, ussdPhoneNumber, 0, hashMap, callbackInvoke)
    }

    /**
     * Invoke a dial-up calling a ussd number
     *
     * @param ussdPhoneNumber ussd number
     * @param simSlot         simSlot number to use
     * @param hashMap             Map of Login and problem messages
     * @param callback  a callback object from return answer
     */
    @SuppressLint("MissingPermission")
    override fun callUSSDInvoke(baseContext: Context, ussdPhoneNumber: String, simSlot: Int,
                                hashMap: HashMap<String, List<String>>, callback: CallbackInvoke) {
        context = baseContext
        callbackInvoke = callback
        map = hashMap
        if (verifyAccesibilityAccess(context)) {
            dialUp(ussdPhoneNumber, simSlot)
        } else {
            callbackInvoke.over("Check your accessibility")
        }
    }

    /**
     * Invoke a dial-up calling a ussd number and
     * you had a overlay progress widget
     *
     * @param ussdPhoneNumber ussd number
     * @param simSlot         simSlot number to use
     * @param hashMap             Map of Login and problem messages
     * @param callback  a callback object from return answer
     */
    @SuppressLint("MissingPermission")
    override fun callUSSDOverlayInvoke(baseContext: Context, ussdPhoneNumber: String, simSlot: Int,
                                       hashMap: HashMap<String, List<String>>, callback: CallbackInvoke) {
        context = baseContext
        callbackInvoke = callback
        map = hashMap
        if (verifyAccesibilityAccess(context) && verifyOverLay(context))
            dialUp(ussdPhoneNumber, simSlot)
        else callbackInvoke.over("Check your accessibility | overlay permission")
    }

    /**
     * Invoke a dial-up calling a ussd number and
     * you had a overlay progress widget
     *
     * @param ussdPhoneNumber ussd number
     * @param simSlot         simSlot number to use
     *
     */
    private fun dialUp(ussdPhoneNumber: String, simSlot: Int) {
        when {
            !map.containsKey(KEY_LOGIN) || !map.containsKey(KEY_ERROR) ->
                callbackInvoke.over("Bad Mapping structure")
            ussdPhoneNumber.isEmpty() -> callbackInvoke.over("Bad ussd number")
            else -> {
                var phone = Uri.encode("#")?.let {
                    ussdPhoneNumber.replace("#", it)
                }
                isRunning = true
                context.startActivity(getActionCallIntent(Uri.parse("tel:$phone"), simSlot))
            }
        }
    }

    /**
     * get action call Intent
     * url: https://stackoverflow.com/questions/25524476/make-call-using-a-specified-sim-in-a-dual-sim-device
     *
     * @param uri     parsed uri to call
     * @param simSlot simSlot number to use
     */
    @SuppressLint("MissingPermission")
    private fun getActionCallIntent(uri: Uri?, simSlot: Int): Intent {
        val telcomManager = context.getSystemService(Context.TELECOM_SERVICE) as? TelecomManager
        return Intent(Intent.ACTION_CALL, uri).apply {
            simSlotName.map { sim -> putExtra(sim, simSlot) }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("com.android.phone.force.slot", true)
            putExtra("Cdma_Supp", true)
            telcomManager?.callCapablePhoneAccounts?.let { handles ->
                if (handles.size > simSlot)
                    putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", handles[simSlot])
            }
        }
    }

    override fun sendData(text: String) = USSDServiceKT.send(text)

    override fun send(text: String, callbackMessage: (String) -> Unit) {
        this.callbackMessage = callbackMessage
        sendType = true
        ussdInterface?.sendData(text)
    }

    override fun cancel() = USSDServiceKT.cancel()

    /**
     * Invoke class to comunicate messages between USSD and App
     */
    interface CallbackInvoke {
        fun responseInvoke(message: String)
        fun over(message: String)
    }

    fun verifyAccesibilityAccess(context: Context): Boolean =
            isAccessibilityServicesEnable(context).also {
                if (!it) openSettingsAccessibility(context as Activity)
            }

    fun verifyOverLay(context: Context): Boolean = (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
            || Settings.canDrawOverlays(context)).also {
        if (!it) openSettingsOverlay(context as Activity)
    }

    private fun openSettingsAccessibility(activity: Activity) =
            with(AlertDialog.Builder(activity)) {
                setTitle("USSD Accessibility permission")
                setMessage("You must enable accessibility permissions for the app %s".format(getNameApp(activity)))
                setCancelable(true)
                setNeutralButton("ok") { _, _ ->
                    activity.startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
                }
                create().show()
            }

    private fun openSettingsOverlay(activity: Activity) =
            with(AlertDialog.Builder(activity)) {
                setTitle("USSD Overlay permission")
                setMessage("You must allow for the app to appear '${getNameApp(activity)}' on top of other apps.")
                setCancelable(true)
                setNeutralButton("ok") { _, _ ->
                    activity.startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:${activity.packageName}")))
                }
                create().show()
            }

    private fun getNameApp(activity: Activity): String = when (activity.applicationInfo.labelRes) {
        0 -> activity.applicationInfo.nonLocalizedLabel.toString()
        else -> activity.getString(activity.applicationInfo.labelRes)
    }

    private fun isAccessibilityServicesEnable(context: Context): Boolean {
        (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager)?.apply {
            installedAccessibilityServiceList.forEach { service ->
                if (service.id.contains(context.packageName) &&
                        Settings.Secure.getInt(context.applicationContext.contentResolver,
                                Settings.Secure.ACCESSIBILITY_ENABLED) == 1)
                    Settings.Secure.getString(context.applicationContext.contentResolver,
                            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)?.let {
                        if (it.split(':').contains(service.id)) return true
                    }
            }
        }
        return false
    }
}
