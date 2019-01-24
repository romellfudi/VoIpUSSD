package com.romellfudi.ussdlibrary;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.provider.Settings;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Romell Dominguez
 * @version 1.1.c 27/09/2018
 * @since 1.0.a
 */
public class USSDController implements USSDInterface{

    protected static USSDController instance;

    protected Context context;

    protected HashMap<String,HashSet<String>> map;

    protected CallbackInvoke callbackInvoke;

    protected CallbackMessage callbackMessage;

    protected static final String KEY_LOGIN = "KEY_LOGIN";

    protected static final String KEY_ERROR = "KEY_ERROR";

    private USSDInterface ussdInterface;

    /**
     * The Singleton building method
     * @param context An activity that could call
     * @return An instance of USSDController
     */
    public static USSDController getInstance(Context context) {
        if (instance == null)
            instance = new USSDController(context);
        return instance;
    }

    private USSDController(Context context) {
        ussdInterface = this;
        this.context = context;
    }

    /**
     * Invoke a dial-up calling a ussd number
     * @param ussdPhoneNumber ussd number
     * @param map Map of Login and problem messages
     * @param callbackInvoke a callback object from return answer
     */
    public void callUSSDInvoke(int simSlot,String ussdPhoneNumber, HashMap<String,HashSet<String>> map, CallbackInvoke callbackInvoke) {
        callUSSDInvoke(ussdPhoneNumber, simSlot, map, callbackInvoke);
    }

    /**
     * Invoke a dial-up calling a ussd number
     * @param ussdPhoneNumber ussd number
     * @param simSlot simSlot number to use
     * @param map Map of Login and problem messages
     * @param callbackInvoke a callback object from return answer
     */
    @SuppressLint("MissingPermission")
    public void callUSSDInvoke(String ussdPhoneNumber, int simSlot, HashMap<String,HashSet<String>> map, CallbackInvoke callbackInvoke) {
        this.callbackInvoke = callbackInvoke;
        this.map = map;

        if (map==null || (!map.containsKey(KEY_ERROR) || !map.containsKey(KEY_LOGIN)) ){
            callbackInvoke.over("Bad Mapping structure");
            return;
        }

        if (ussdPhoneNumber.isEmpty()) {
            callbackInvoke.over("Bad ussd number");
            return;
        }
        if (verifyAccesibilityAccess(context)) {
            String uri = Uri.encode("#");
            if (uri != null)
                ussdPhoneNumber = ussdPhoneNumber.replace("#", uri);
            Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);
            if (uriPhone != null)
                context.startActivity(getActionCallIntent(uriPhone, simSlot));
        }
    }

    /**
     * get action call Intent
     * @param uri parsed uri to call
     * @param simSlot simSlot number to use
     */
    @SuppressLint("MissingPermission")
    private Intent getActionCallIntent(Uri uri, int simSlot) {
        // https://stackoverflow.com/questions/25524476/make-call-using-a-specified-sim-in-a-dual-sim-device
        final String simSlotName[] = {
                "extra_asus_dial_use_dualsim",
                "com.android.phone.extra.slot",
                "slot",
                "simslot",
                "sim_slot",
                "subscription",
                "Subscription",
                "phone",
                "com.android.phone.DialingMode",
                "simSlot",
                "slot_id",
                "simId",
                "simnum",
                "phone_type",
                "slotId",
                "slotIdx"
        };


        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("com.android.phone.force.slot", true);
        intent.putExtra("Cdma_Supp", true);

        for (String s : simSlotName)
            intent.putExtra(s, simSlot);

        TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
        if(telecomManager != null) {
            List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
            if (phoneAccountHandleList != null && phoneAccountHandleList.size() > simSlot)
                intent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", phoneAccountHandleList.get(simSlot));
        }

        return intent;
    }

    @Override
    public void sendData(String text) {
        USSDService.send(text);
    }

    public void send(String text, CallbackMessage callbackMessage){
        this.callbackMessage = callbackMessage;
        ussdInterface.sendData(text);
    }

    public static boolean verifyAccesibilityAccess(Context context) {
        boolean isEnabled = USSDController.isAccessiblityServicesEnable(context);
        if (!isEnabled) {
            if(context instanceof Activity) {
                openSettingsAccessibility((Activity) context);
            } else {
                Toast.makeText(
                        context,
                        "voipUSSD accessibility service is not enabled",
                        Toast.LENGTH_LONG
                ).show();

            }
        }
        return isEnabled;
    }

    private static void openSettingsAccessibility(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Need accessibility permission");
        ApplicationInfo applicationInfo = activity.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        String name = applicationInfo.labelRes == 0 ?
                applicationInfo.nonLocalizedLabel.toString() : activity.getString(stringId);
        alertDialogBuilder
                .setMessage("You must enable accessibility permissions for the app '" + name + "'");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                activity.startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        if (alertDialog != null) {
            alertDialog.show();
        }
    }


    protected static boolean isAccessiblityServicesEnable(Context context) {
        AccessibilityManager am = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (am != null) {
            for (AccessibilityServiceInfo service : am.getInstalledAccessibilityServiceList()) {
                if (service.getId().contains(context.getPackageName())) {
                    return USSDController.isAccessibilitySettingsOn(context, service.getId());
                }
            }
        }
        return false;
    }

    protected static boolean isAccessibilitySettingsOn(Context context, final String service) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                    context.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            //
        }
        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
                splitter.setString(settingValue);
                while (splitter.hasNext()) {
                    String accessabilityService = splitter.next();
                    if (accessabilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public interface CallbackInvoke {
        void responseInvoke(String message);
        void over(String message);
    }

    public interface CallbackMessage {
        void responseMessage(String message);
    }
}
