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
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

/**
 *
 * @author Romell Dominguez
 * @version 1.0.a 23/02/2017
 * @since 1.0
 */
public class USSDController {

    protected static USSDController instance;

    protected Activity context;

    protected static TextView result;

    protected Callback callback;

    public static USSDController getInstance(Activity activity) {
        if (instance == null)
            instance = new USSDController(activity);
        return instance;
    }

    private USSDController(Activity activity) {
        context = activity;
    }

    @SuppressLint("MissingPermission")
    public void callUSSDInvoke(String ussdPhoneNumber, TextView resultCallback, Callback callback) {
        result = resultCallback;
        this.callback = callback;
        result.setText("");

        if (ussdPhoneNumber.isEmpty()) {
            callback.over();
            return;
        }
        if (verificarServicioAccesibilidad(context)) {
            String uri = Uri.encode("#");
            if (uri != null)
                ussdPhoneNumber = ussdPhoneNumber.replace("#", uri);
            Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);
            if (uriPhone != null)
                context.startActivity(new Intent(Intent.ACTION_CALL, uriPhone));
        }
    }

    private boolean verificarServicioAccesibilidad(Activity act) {
        boolean isEnabled = USSDController.isAccessiblityServicesEnable(act);
        if (!isEnabled) {
            openSettingsAccessibility(act);
        }
        return isEnabled;
    }

    private void openSettingsAccessibility(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle("Permisos de accesibilidad");
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        String name = applicationInfo.labelRes == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
        alertDialogBuilder
                .setMessage("Debe habilitar los permisos de accesibilidad para la app '" + name + "'");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setNeutralButton("Entendido", new DialogInterface.OnClickListener() {
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
                ;
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

    protected void inject(String response) {
        result.append("\n-\n" + response);
    }

    public interface Callback {
        void over();
    }
}
