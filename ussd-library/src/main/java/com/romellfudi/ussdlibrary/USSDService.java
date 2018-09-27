package com.romellfudi.ussdlibrary;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * AccessibilityService for ussd windows on Android mobile Telcom
 *
 * @author Romell Dominguez
 * @version 1.1.b 27/09/2018
 * @since 1.0.a
 */
public class USSDService extends AccessibilityService {

    public static String TAG = USSDService.class.getSimpleName();

    private static final String LABEL_SEND = "send";

    private static final String LABEL_CANCELAR = "cancel";

    private static final String LABEL_OK = "ok";

    private static AccessibilityEvent event;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        this.event=event;

        Log.d(TAG, "onAccessibilityEvent");

        Log.d(TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                event.getEventType(), event.getClassName(), event.getPackageName(),
                event.getEventTime(), event.getText()));


        if (hasProblem(event) || LogView(event)) {
            // deal down
            clickOnButton(event, LABEL_SEND);
            USSDController.instance.callbackInvoke.over(event.getText().get(0).toString());
        } else if (isUSSDNeed(event)) {
            // ready for work
            String response = event.getText().get(0).toString();
            if (response.contains("\n")) {
                response = response.substring(response.indexOf('\n') + 1);
            }
            if (notInputText(event)) {
                // not more input panels / LAST MESSAGE
                // sent 'OK' button
                clickOnButton(event, LABEL_OK);
                USSDController.instance.callbackInvoke.over(response);
            } else {
                // sent option 1
                if (USSDController.instance.callbackMessage == null)
                    USSDController.instance.callbackInvoke.responseInvoke(response);
                else {
                    USSDController.instance.callbackMessage.responseMessage(response);
                    USSDController.instance.callbackMessage = null;
                }
            }
        } else if (LogView(event) && notInputText(event)) {
            // first view or logView, do nothing, pass / FIRST MESSAGE
            clickOnButton(event, LABEL_OK);
            USSDController.instance.callbackInvoke.over(event.getText().get(0).toString());
        }

    }

    public static void send(String text) {
        setTextIntoField(event, text);
        clickOnButton(event, LABEL_SEND);
    }

    private static void setTextIntoField(AccessibilityEvent event, String data) {
        USSDController ussdController = USSDController.instance;
        Bundle arguments = new Bundle();
        arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, data);
        for (int i = 0; i < event.getSource().getChildCount(); i++) {
            AccessibilityNodeInfo node = event.getSource().getChild(i);
            Log.d(TAG, i + ":" + node.getClassName());
            if (node != null && node.getClassName().equals("android.widget.EditText")
                    && !node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)) {
                ((ClipboardManager) ussdController.context
                        .getSystemService(Context.CLIPBOARD_SERVICE))
                        .setPrimaryClip(ClipData.newPlainText("text", data));
                node.performAction(AccessibilityNodeInfo.ACTION_PASTE);
            }
        }
    }

    private static boolean notInputText(AccessibilityEvent event) {
        boolean flag = true;
        for (int i = 0; i < event.getSource().getChildCount(); i++) {
            AccessibilityNodeInfo node = event.getSource().getChild(i);
            if (node != null && node.getClassName().equals("android.widget.EditText"))
                flag = false;
        }
        return flag;
    }

    private boolean isUSSDWidget(AccessibilityEvent event) {
        return (event.getClassName().equals("amigo.app.AmigoAlertDialog")
                || event.getClassName().equals("android.app.AlertDialog"));
    }


    private boolean isUSSDNeed(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && (event.getText().get(0).toString().contains(":"));
    }

    private boolean LogView(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && (event.getText().get(0).toString().toLowerCase().contains("espere"));
    }

    protected boolean hasProblem(AccessibilityEvent event) {
        return isUSSDWidget(event)
                && (event.getText().get(0).toString().toLowerCase().contains("problema")
                || event.getText().get(0).toString().toLowerCase().contains("desconocido"));
    }


    private static void clickOnButton(AccessibilityEvent event, String label) {
        if (event.getSource() != null) {
            for (AccessibilityNodeInfo nodeButton
                    : event.getSource().findAccessibilityNodeInfosByText(label)) {
                nodeButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected");
    }
}