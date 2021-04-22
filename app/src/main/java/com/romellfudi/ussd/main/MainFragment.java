/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.javafaker.Faker;
import com.romellfudi.permission.PermissionService;
import com.romellfudi.ussd.App;
import com.romellfudi.ussd.R;
import com.romellfudi.ussd.databinding.ContentOp1Binding;
import com.romellfudi.ussdlibrary.OverlayShowingService;
import com.romellfudi.ussdlibrary.SplashLoadingService;
import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Use Case for Test Windows
 *
 * @author Romell Domínguez
 * @version 1.1.b 27/09/2018
 * @since 1.0.a
 */
public class MainFragment extends Fragment {

    @Inject
    USSDApi ussdApi;

    @Inject
    HashMap<String, HashSet<String>> map;

    @Inject
    String[] info;

    @Inject
    Faker faker;

    DaoViewModel mViewModel;
    ContentOp1Binding binding;
    private int simSelected;


    @SuppressLint("MissingPermission")
    private void callSecondSIM(View view, int simSlot) {
        String ussdPhoneNumber = binding.phone.toString().trim();
        if (ussdPhoneNumber.contains("#"))
            ussdPhoneNumber = ussdPhoneNumber.replace("#", Uri.encode("#"));
        Uri uriPhone = Uri.parse("tel:" + ussdPhoneNumber);

        Intent intent = new Intent(Intent.ACTION_CALL, uriPhone);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("com.android.phone.force.slot", true);
        intent.putExtra("Cdma_Supp", true);
        for (String s : info)
            intent.putExtra(s, simSlot);
        TelecomManager telecomManager = (TelecomManager) getContext().getSystemService(Context.TELECOM_SERVICE);
        List<PhoneAccountHandle> phoneAccountHandleList = telecomManager.getCallCapablePhoneAccounts();
        if (phoneAccountHandleList != null && phoneAccountHandleList.size() > simSlot)
            intent.putExtra("android.telecom.extra.PHONE_ACCOUNT_HANDLE", phoneAccountHandleList.get(simSlot));
        startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getActivity().getApplicationContext()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        new PermissionService(getActivity()).request(callback);
        mViewModel = ViewModelProviders.of(getActivity()).get(DaoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.content_op1, container, false);
        Objects.requireNonNull(getActivity());
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(getActivity());
        setHasOptionsMenu(false);
        binding.btn1.setOnClickListener(view -> {
            binding.result.setText("");
            ussdApi.callUSSDInvoke(getPhoneNumber(), map, new USSDController.CallbackInvoke() {
                @Override
                public void responseInvoke(String message) {
                    Timber.d(message);
                    binding.result.append("\n-\n" + message);
                    // first option list - select option 1
                    ussdApi.send("1", message12 -> {
                        Timber.i(message12);
                        binding.result.append("\n-\n" + message12);
                        // second option list - select option 2
                        ussdApi.send("2", message121 -> {
                            Timber.i(message121);
                            binding.result.append("\n-\n" + message121);
                            // second option list - select option 1
                            ussdApi.send("1", message1211 -> {
                                Timber.i(message1211);
                                binding.result.append("\n-\n" + message1211);
                                Timber.i("successful");
                            });
                        });
                    });
//                        ussdApi.cancel();
                }

                @Override
                public void over(String message) {
                    Timber.i(message);
                    binding.result.append("\n-\n" + message);
//                        mViewModel.setResult(dao);
//                        mViewModel.update();
                }
            });
        });

        binding.btn2.setOnClickListener(view -> {
            if (USSDController.verifyOverLay(getActivity())) {
                final Intent svc = new Intent(getActivity(), OverlayShowingService.class);
                svc.putExtra(OverlayShowingService.EXTRA, getString(R.string.process));
                pendingServiceIntent(svc);
                callOverlay(svc);
            }
        });

        binding.btn4.setOnClickListener(view -> {
            if (USSDController.verifyOverLay(getActivity())) {
                final Intent svc = new Intent(getActivity(), SplashLoadingService.class);
                pendingServiceIntent(svc);
                callOverlay(svc);
            }
        });

        binding.btn3.setOnClickListener(view ->
                USSDController.verifyAccesibilityAccess(getActivity()));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.all_sims, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.simSpinner.setAdapter(adapter);
        binding.simSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                simSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                simSelected = 0;
            }
        });
        binding.btn5.setOnClickListener(view -> callSecondSIM(view, simSelected));

        new Handler().postDelayed(() -> binding.phone.setText(faker.phoneNumber().cellPhone()), 1000);
        return binding.getRoot();
    }


    private void callOverlay(Intent overlayDialogService) {
        ussdApi.callUSSDOverlayInvoke(getPhoneNumber(), map, new USSDController.CallbackInvoke() {
            @Override
            public void responseInvoke(String message) {
                Timber.i(message);
                binding.result.append("\n-\n" + message);
                // first option list - select option 1
                ussdApi.send("1", message1 -> {
                    Timber.i(message1);
                    binding.result.append("\n-\n" + message1);
                    // second option list - select option 2
                    ussdApi.send("2", message2 -> {
                        Timber.i(message2);
                        binding.result.append("\n-\n" + message2);
                        // second option list - select option 1
                        ussdApi.send("1", message3 -> {
                            Timber.i(message3);
                            binding.result.append("\n-\n" + message3);
                            getActivity().stopService(overlayDialogService);
                            Timber.i("successful");
                        });
                    });
                });
//                            ussdApi.cancel();
            }

            @Override
            public void over(String message) {
                Timber.i(message);
                binding.result.append("\n-\n" + message);
                getActivity().stopService(overlayDialogService);
                Timber.i("STOP OVERLAY DIALOG");
            }
        });
    }

    private void pendingServiceIntent(Intent overlayService) {
        getActivity().startService(overlayService);
        Timber.i(getString(R.string.overlayDialog));
        new Handler().postDelayed(() -> getActivity().stopService(overlayService), 12000);
        binding.result.setText("");
    }

    private String getPhoneNumber() {
        return binding.phone.getText().toString().trim();
    }

    private PermissionService.Callback callback = new PermissionService.Callback() {
        @Override
        public void onResponse(ArrayList<String> refusePermissions) {
            if (refusePermissions != null) {
                Toast.makeText(getContext(),
                        getString(R.string.refuse_permissions), Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Timber.i(MessageFormat.format(getString(R.string.primissionsLogFormat), permissions, grantResults));
        callback.handler(permissions, grantResults);
    }
}

