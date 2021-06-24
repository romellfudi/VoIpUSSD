/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */
package com.romellfudi.ussdlibrary;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * AccessibilityService object for ussd dialogs on Android mobile Telcoms
 *
 * @author Romell Dominguez
 * @version 1.1.c 27/09/2018
 * @since 1.0.a
 */
public class USSDServiceKT extends AccessibilityService {

    private static AccessibilityEvent event;

    /**
     * Catch widget by Accessibility, when is showing at mobile display
     *
     * @param event AccessibilityEvent
     */
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        USSDServiceKT.event = event;
        USSDController ussd = USSDController.INSTANCE;
        Timber.d(String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                event.getEventType(), event.getClassName(), event.getPackageName(),
                event.getEventTime(), event.getText()));
        if (ussd == null || !ussd.isRunning()) {
            return;
        }
        String response = event.getText().toString();
        if (LoginView(event) && notInputText(event)) {
            // first view or logView, do nothing, pass / FIRST MESSAGE
            clickOnButton(event, 0);
            ussd.stopRunning();
            ussd.callbackInvoke.over(response);
        } else if (problemView(event) || LoginView(event)) {
            // deal down
            clickOnButton(event, 1);
            ussd.callbackInvoke.over(response);
        } else if (isUSSDWidget(event)) {
            Timber.d("catch a USSD widget/Window");
            if (notInputText(event)) {
                // not more input panels / LAST MESSAGE
                // sent 'OK' button
                Timber.d("No inputText found & closing USSD process");
                clickOnButton(event, 0);
                ussd.stopRunning();
                ussd.callbackInvoke.over(response);
            } else {
                // sent option 1
                if (ussd.getSendType())
                    ussd.getCallbackMessage().invoke(response);
                else ussd.callbackInvoke.responseInvoke(response);
            }
        }

    }

    /**
     * Send whatever you want via USSD
     *
     * @param text any string
     */
    public static void send(String text) {
        Timber.d("trying to send... %s", text);
        setTextIntoField(event, text);
        clickOnButton(event, 1);
    }

    /**
     * Dismiss dialog by using first button from USSD Dialog
     */
    public static void cancel() {
        clickOnButton(event, 0);
        Timber.d("Trying to close/cancel USSD process by clicked in first button ");
    }

    /**
     * set text into input text at USSD widget
     *
     * @param event AccessibilityEvent
     * @param data  Any String
     */
    private static void setTextIntoField(AccessibilityEvent event, String data) {
        Bundle arguments = new Bundle();
        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, data);
        for (AccessibilityNodeInfo leaf : getLeaves(event)) {
            if (leaf.getClassName().equals("android.widget.EditText")
                    && !leaf.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)) {
                ClipboardManager clipboardManager = ((ClipboardManager)  USSDController
                        .INSTANCE.getContext().getSystemService(Context.CLIPBOARD_SERVICE));
                if (clipboardManager != null) {
                    clipboardManager.setPrimaryClip(ClipData.newPlainText("text", data));
                }
                leaf.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
        }
    }

    /**
     * Method evaluate if USSD widget has input text
     *
     * @param event AccessibilityEvent
     * @return boolean has or not input text
     */
    protected static boolean notInputText(AccessibilityEvent event) {
        for (AccessibilityNodeInfo leaf : getLeaves(event))
            if (leaf.getClassName().equals("android.widget.EditText")) return false;
        return true;
    }

    /**
     * The AccessibilityEvent is instance of USSD Widget class
     *
     * @param event AccessibilityEvent
     * @return boolean AccessibilityEvent is USSD
     */
    private boolean isUSSDWidget(AccessibilityEvent event) {
        return (event.getClassName().equals("amigo.app.AmigoAlertDialog")
                || event.getClassName().equals("android.app.AlertDialog")
                || event.getClassName().equals("com.android.phone.oppo.settings.LocalAlertDialog")
                || event.getClassName().equals("com.zte.mifavor.widget.AlertDialog")
                || event.getClassName().equals("color.support.v7.app.AlertDialog"));
    }

    /**
     * The View has a login message into USSD Widget
     *
     * @param event AccessibilityEvent
     * @return boolean USSD Widget has login message
     */
    private boolean LoginView(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && USSDController.INSTANCE.getMap().get(USSDController.KEY_LOGIN)
                .contains(event.getText().get(0).toString());
    }

    /**
     * The View has a problem message into USSD Widget
     *
     * @param event AccessibilityEvent
     * @return boolean USSD Widget has problem message
     */
    protected boolean problemView(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && USSDController.INSTANCE.getMap().get(USSDController.KEY_ERROR)
                .contains(event.getText().get(0).toString());
    }

    /**
     * click a button using the index
     *
     * @param event AccessibilityEvent
     * @param index button's index
     */
    protected static void clickOnButton(AccessibilityEvent event, int index) {
        int count = -1;
        for (AccessibilityNodeInfo leaf : getLeaves(event)) {
            if (leaf.getClassName().toString().toLowerCase().contains("button")) {
                count++;
                if (count == index) {
                    leaf.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
    }

    private static List<AccessibilityNodeInfo> getLeaves(AccessibilityEvent event) {
        List<AccessibilityNodeInfo> leaves = new ArrayList<>();
        if (event.getSource() != null) {
            getLeaves(leaves, event.getSource());
        }
        return leaves;
    }

    private static void getLeaves(List<AccessibilityNodeInfo> leaves, AccessibilityNodeInfo node) {
        if (node.getChildCount() == 0) {
            leaves.add(node);
            return;
        }
        for (int i = 0; i < node.getChildCount(); i++) {
            getLeaves(leaves, node.getChild(i));
        }
    }

    /**
     * Active when SO interrupt the application
     */
    @Override
    public void onInterrupt() {
        Timber.d( "onInterrupt");
    }

    /**
     * Configure accessibility server from Android Operative System
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Timber.d("onServiceConnected");
    }
}
