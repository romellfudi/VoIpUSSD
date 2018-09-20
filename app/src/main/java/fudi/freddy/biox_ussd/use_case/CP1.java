package fudi.freddy.biox_ussd.use_case;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fudi.freddy.biox_ussd.R;
import com.romellfudi.permission.PermissionService;
import com.romellfudi.ussdlibrary.OverlayShowingService;
import com.romellfudi.ussdlibrary.USSDController;

import android.Manifest.permission;

/**
 * Use Case for Test Windows
 *
 * @author Romell Domínguez
 * @version 1.0.a 23/02/2017
 * @since 1.0
 */
public class CP1 extends Fragment {

    TextView result;
    EditText phone;
    Button btn ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new PermissionService(getActivity()).request(
                new String[]{permission.CALL_PHONE},callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_op1, container, false);
        result = view.findViewById(R.id.result);
        phone = view.findViewById(R.id.phone);
        btn = view.findViewById(R.id.btn);
        setHasOptionsMenu(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent svc = new Intent(getActivity(), OverlayShowingService.class);
                svc.putExtra(OverlayShowingService.EXTRA,"PROCESANDO");
                getActivity().startService(svc);
                String phoneNumber = phone.getText().toString().trim();
                USSDController ussdController = USSDController.getInstance(getActivity());
                ussdController.callUSSDInvoke(phoneNumber, result, new USSDController.Callback() {
                    @Override
                    public void over() {
                        Log.d("DDD","stop");
                        getActivity().stopService(svc);
                    }
                });
            }
        });

        return view;
    }

    private PermissionService.Callback callback = new PermissionService.Callback() {
        @Override
        public void onRefuse(ArrayList<String> RefusePermissions) {
            Toast.makeText(getContext(),
                    getString(R.string.refuse_permissions),
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        @Override
        public void onFinally() {
            // pass
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getActivity())) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getActivity().getPackageName()));
                    startActivity(intent);
                }
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callback.handler(permissions, grantResults);
    }
}

