package fudi.freddy.ussdlibrary;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

/**
 * Created by romelldominguez on 6/2/16.
 */
public class USSDService extends AccessibilityService {

    public static String TAG = USSDService.class.getSimpleName();

    private static final String LABEL_SEND = "send";

    private static final String LABEL_CANCELAR = "Cancel";

    private static final String LABEL_OK = "ok";


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

//        if (!USSDController.service)
//            return;

        Log.d(TAG, "onAccessibilityEvent");

        Log.d(TAG, String.format(
                "onAccessibilityEvent: [type] %s [class] %s [package] %s [time] %s [text] %s",
                event.getEventType(), event.getClassName(), event.getPackageName(),
                event.getEventTime(), event.getText()));


        if (errorInicio(event) || vistaLogeo(event)) {
            clickOnButton(event, LABEL_SEND);
        } else if (ambienteTrabajo(event)) {
            String response = event.getText().get(0).toString();
            if (response.contains("\n")) {
                response = response.substring(response.indexOf('\n') + 1);
            }
            USSDController ussdController = USSDController.instance;
            ussdController.result.append(response);
            if (notInputText(event))
                clickOnButton(event,LABEL_OK);
            else {
                setTextIntoField(event, "1");
                clickOnButton(event, LABEL_SEND);
            }
        } else if (vistaUSSD(event)) {
            clickOnButton(event, LABEL_CANCELAR);
        }

    }

    private static void setTextIntoField(AccessibilityEvent event, String data) {
        USSDController ussdController = USSDController.instance;
        Bundle arguments = new Bundle();
        arguments.putCharSequence(
                AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, data);
        for (int i = 0; i < event.getSource().getChildCount(); i++) {
            AccessibilityNodeInfo node = event.getSource().getChild(i);
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
             if(node.getClassName().equals("android.widget.EditText"))
                 flag = false;
        }
        return flag;
    }

    private boolean vistaUSSD(AccessibilityEvent event) {
        return (event.getClassName().equals("amigo.app.AmigoAlertDialog")
                || event.getClassName().equals("android.app.AlertDialog"));
    }


    private boolean ambienteTrabajo(AccessibilityEvent event) {
        return vistaUSSD(event)
                && (event.getText().get(0).toString().startsWith("NA"));
    }

    private boolean vistaLogeo(AccessibilityEvent event) {
        return vistaUSSD(event)
                && (event.getText().get(0).toString().toLowerCase().contains("espere"));
    }

    protected boolean errorInicio(AccessibilityEvent event) {
        return vistaUSSD(event)
                && (event.getText().get(0).toString().toLowerCase().contains("problema")
                || event.getText().get(0).toString().toLowerCase().contains("desconocido")
                || event.getText().get(0).toString().toLowerCase().contains("servicio"));
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
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.packageNames = new String[]{"com.android.phone"};
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 0;
        setServiceInfo(info);
    }
}