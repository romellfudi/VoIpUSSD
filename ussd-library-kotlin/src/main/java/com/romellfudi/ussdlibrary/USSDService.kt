package com.romellfudi.ussdlibrary

import android.accessibilityservice.AccessibilityService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.*

/**
 * AccessibilityService for ussd windows on Android mobile Telcom
 *
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
class USSDService : AccessibilityService() {

    /**
     * Catch widget by Accessibility, when is showing at mobile display
     * @param event AccessibilityEvent
     */
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent")
        Log.d(TAG, "onAccessibilityEvent: [type] ${event?.eventType} [class] ${event?.className}" +
                " [package] ${event?.packageName} [time]" +
                " ${event?.eventTime} [text] ${event?.text}")

        if (USSDController.instance == null || !USSDController.instance!!.isRunning!!) {
            return
        }

        if (LoginView(event) && notInputText(event)) {
            // first view or logView, do nothing, pass / FIRST MESSAGE
            clickOnButton(event, 0)
            USSDController.instance!!.callbackInvoke.over(event.text[0].toString())
        } else if (problemView(event) ) {
            // deal down
            clickOnButton(event, 1)
            USSDController.instance!!.isRunning = false
            USSDController.instance!!.callbackInvoke.over(event.text[0].toString())
        } else if (isUSSDWidget(event)) {
            // ready for work
            var response = event.text[0].toString()
            if (response.contains("\n")) {
                response = response.substring(response.indexOf("\n") + 1)
            }
            if (notInputText(event)) {
                // not more input panels / LAST MESSAGE
                // sent 'OK' button
                clickOnButton(event, 0)
                USSDController.instance!!.isRunning = false
                USSDController.instance!!.callbackInvoke.over(response)
            } else {
                // sent option 1
                if (USSDController.instance!!.callbackMessage == null)
                    USSDController.instance!!.callbackInvoke.responseInvoke(response)
                else {
                    USSDController.instance!!.callbackMessage(response)
                    USSDController.instance!!.callbackMessage.let { null }
                }
            }
        }

    }

    /**
     * The AccessibilityEvent is instance of USSD Widget class
     * @param event AccessibilityEvent
     * @return boolean AccessibilityEvent is USSD
     */
    private fun isUSSDWidget(event: AccessibilityEvent): Boolean {
        return event.className == "amigo.app.AmigoAlertDialog" || event.className == "android.app.AlertDialog"
    }

    /**
     * The View has a login message into USSD Widget
     * @param event AccessibilityEvent
     * @return boolean USSD Widget has login message
     */
    private fun LoginView(event: AccessibilityEvent): Boolean {
        return isUSSDWidget(event) && USSDController.instance!!.map!![USSDController.KEY_LOGIN]!!
                .contains(event.text[0].toString())
    }

    /**
     * The View has a problem message into USSD Widget
     * @param event AccessibilityEvent
     * @return boolean USSD Widget has problem message
     */
    private fun problemView(event: AccessibilityEvent): Boolean {
        return isUSSDWidget(event) && USSDController.instance!!.map!![USSDController.KEY_ERROR]!!
                .contains(event.text[0].toString())
    }

    /**
     * Active when SO interrupt the application
     */
    override fun onInterrupt() {
        Log.d(TAG, "onInterrupt")
    }

    /**
     * Configure accessibility server from Android Operative System
     */
    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d(TAG, "onServiceConnected")
    }

    private val TAG = USSDService::class.simpleName//.getSimpleName()

    companion object {

        private var event: AccessibilityEvent? = null

        /**
         * Send whatever you want via USSD
         * @param text any string
         */
        internal fun send(text: String) {
            setTextIntoField(event, text)
            clickOnButton(event, 1)
        }

        /**
         * set text into input text at USSD widget
         * @param event AccessibilityEvent
         * @param data Any String
         */
        private fun setTextIntoField(event: AccessibilityEvent?, data: String) {
            val ussdController = USSDController.instance
            val arguments = Bundle()
            arguments.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, data)

            for (leaf in getLeaves(event!!)) {
                if (leaf.className == "android.widget.EditText" && !leaf.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)) {
                    val clipboardManager = ussdController?.context
                            ?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    if (clipboardManager != null) {
                        clipboardManager.primaryClip = ClipData.newPlainText("text", data)
                    }

                    leaf.performAction(AccessibilityNodeInfo.ACTION_PASTE)
                }
            }
        }

        /**
         * Method evaluate if USSD widget has input text
         * @param event AccessibilityEvent
         * @return boolean has or not input text
         */
        internal fun notInputText(event: AccessibilityEvent): Boolean {
            var flag = true
            for (leaf in getLeaves(event)) {
                if (leaf.className == "android.widget.EditText") flag = false
            }
            return flag
        }

        /**
         * click a button using the index
         * @param event AccessibilityEvent
         * @param index button's index
         */
        private fun clickOnButton(event: AccessibilityEvent?, index: Int) {
            var count = -1
            for (leaf in getLeaves(event!!)) {
                if (leaf.className.toString().toLowerCase().contains("button")) {
                    count++
                    if (count == index) {
                        leaf.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    }
                }
            }
        }

        private fun getLeaves(event: AccessibilityEvent): List<AccessibilityNodeInfo> {
            val leaves = ArrayList<AccessibilityNodeInfo>()
            if (event.source != null) {
                getLeaves(leaves, event.source)
            }
            return leaves
        }

        private fun getLeaves(leaves: MutableList<AccessibilityNodeInfo>, node: AccessibilityNodeInfo) {
            if (node.childCount == 0) {
                leaves.add(node)
                return
            }
            (0..node.childCount).forEach {
                getLeaves(leaves, node.getChild(it))
            }
        }
    }
}