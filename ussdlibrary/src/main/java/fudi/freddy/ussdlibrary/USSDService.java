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
 * AccessibilityService for ussd windows on Android mobile Telcom
 *
 * @author Romell Domínguez
 * @version 1.0.a 23/02/2017
 * @since 1.0
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
            Log.d(TAG,"d");
            clickOnButton(event, LABEL_SEND);
        } else if (ambienteTrabajo(event)) {
            String response = event.getText().get(0).toString();
            if (response.contains("\n")) {
                response = response.substring(response.indexOf('\n') + 1);
            }
            USSDController.instance.inject(response);
            if (notInputText(event)) {
                Log.d(TAG, "a");
                clickOnButton(event, LABEL_OK);
            }else {
                Log.d(TAG,"c");
                setTextIntoField(event, "1");
                clickOnButton(event, LABEL_SEND);
            }
        } else if (vistaUSSD(event)) {
            Log.d(TAG,"b");
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
            Log.d(TAG,i+":"+node.getClassName());
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
                && (event.getText().get(0).toString().contains(":"));
    }

    private boolean vistaLogeo(AccessibilityEvent event) {
        return vistaUSSD(event)
                && (event.getText().get(0).toString().toLowerCase().contains("espere"));
    }

    protected boolean errorInicio(AccessibilityEvent event) {
        return vistaUSSD(event)
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