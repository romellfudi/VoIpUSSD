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
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager
import java.util.*

/**
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
class USSDController private constructor(var context: Context) : USSDInterface, USSDApi {

    lateinit var map: HashMap<String, HashSet<String>>

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
     * @param map             Map of Login and problem messages
     * @param callbackInvoke  a callback object from return answer
     */
    override fun callUSSDInvoke(ussdPhoneNumber: String, map: HashMap<String, HashSet<String>>,
                                callbackInvoke: CallbackInvoke) {
        sendType = false
        callUSSDInvoke(ussdPhoneNumber, 0, map, callbackInvoke)
    }

    /**
     * Invoke a dial-up calling a ussd number and
     * you had a overlay progress widget
     *
     * @param ussdPhoneNumber ussd number
     * @param hashMap             Map of Login and problem messages
     * @param callbackInvoke  a callback object from return answer
     */
    override fun callUSSDOverlayInvoke(ussdPhoneNumber: String, hashMap: HashMap<String, HashSet<String>>,
                                       callbackInvoke: CallbackInvoke) {
        sendType = false
        callUSSDOverlayInvoke(ussdPhoneNumber, 0, map, callbackInvoke)
    }

    /**
     * Invoke a dial-up calling a ussd number
     *
     * @param ussdPhoneNumber ussd number
     * @param simSlot         simSlot number to use
     * @param hashMap             Map of Login and problem messages
     * @param callbackInvoke  a callback object from return answer
     */
    @SuppressLint("MissingPermission")
    override fun callUSSDInvoke(ussdPhoneNumber: String, simSlot: Int,
                                hashMap: HashMap<String, HashSet<String>>, callback: CallbackInvoke) {
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
     * @param map             Map of Login and problem messages
     * @param callbackInvoke  a callback object from return answer
     */
    @SuppressLint("MissingPermission")
    override fun callUSSDOverlayInvoke(ussdPhoneNumber: String, simSlot: Int,
                                       map: HashMap<String, HashSet<String>>, callback: CallbackInvoke) {
        this.callbackInvoke = callback
        this.map = map
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
            !map.containsKey(KEY_ERROR) || !map.containsKey(KEY_ERROR) -> this.callbackInvoke.over("Bad Mapping structure")
            ussdPhoneNumber.isEmpty() -> this.callbackInvoke.over("Bad ussd number")
            else -> {
                val phone = ussdPhoneNumber.replace("#", Uri.encode("#"))
                isRunning = true
                this.context.startActivity(getActionCallIntent(Uri.parse("tel:$phone"), simSlot))
            }
        }
    }

    /**
     * get action call Intent
     *
     * @param uri     parsed uri to call
     * @param simSlot simSlot number to use
     */
    @SuppressLint("MissingPermission")
    private fun getActionCallIntent(uri: Uri?, simSlot: Int): Intent {
        // https://stackoverflow.com/questions/25524476/make-call-using-a-specified-sim-in-a-dual-sim-device
        val simSlotName = arrayOf("extra_asus_dial_use_dualsim",
                "com.android.phone.extra.slot", "slot", "simslot", "sim_slot", "subscription",
                "Subscription", "phone", "com.android.phone.DialingMode", "simSlot", "slot_id",
                "simId", "simnum", "phone_type", "slotId", "slotIdx")
        val telcomManager = (context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager)
        return Intent(Intent.ACTION_CALL, uri).apply {
            simSlotName.map { sim -> putExtra(sim, simSlot) }
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra("com.android.phone.force.slot", true)
            putExtra("Cdma_Supp", true)
            telcomManager.callCapablePhoneAccounts?.let { handles ->
                if (handles.size > simSlot)
                    putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", handles[simSlot])
            }
        }
    }

    override fun sendData(text: String) = USSDServiceKT.send(text)

    override fun send(text: String, callbackMessage: (String) -> Unit) {
        this.callbackMessage = callbackMessage
        this.sendType = true
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

    companion object {

        // singleton reference
        var instance: USSDController? = null
        val KEY_LOGIN = "KEY_LOGIN"
        val KEY_ERROR = "KEY_ERROR"

        /**
         * The Singleton building method
         *
         * @param context An activity that could call
         * @return An instance of USSDController
         */
        fun getInstance(context: Context): USSDApi =
                instance ?: USSDController(context).also { instance = it }

        fun verifyAccesibilityAccess(context: Context): Boolean =
            isAccessibilityServicesEnable(context).also{
            if (!it) openSettingsAccessibility(context as Activity)
        }

        fun verifyOverLay(context: Context): Boolean
            = (Build.VERSION.SDK_INT < Build.VERSION_CODES.M
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
            (context.getSystemService(Context.ACCESSIBILITY_SERVICE) as? AccessibilityManager)?.let {
                it.installedAccessibilityServiceList.forEach { service ->
                    if (service.id.contains(context.packageName))
                        return isAccessibilitySettingsOn(context, service.id)
                }
            }
            return false
        }

        private fun isAccessibilitySettingsOn(context: Context, service: String): Boolean =
                if (Settings.Secure.getInt(context.applicationContext.contentResolver,
                                Settings.Secure.ACCESSIBILITY_ENABLED) == 1) {
                    Settings.Secure.getString(context.applicationContext.contentResolver,
                            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)?.let {
                        TextUtils.SimpleStringSplitter(':').apply {
                            setString(it)
                            while (hasNext()) if (next() == service) return true
                        }
                    }
                    false
                } else false
    }
}
