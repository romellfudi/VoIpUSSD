/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussd.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.romellfudi.permission.PermissionService;
import com.romellfudi.ussd.App;
import com.romellfudi.ussd.R;
import com.romellfudi.ussd.databinding.ContentOp1Binding;
import com.romellfudi.ussdlibrary.OverlayShowingService;
import com.romellfudi.ussdlibrary.SplashLoadingService;
import com.romellfudi.ussdlibrary.USSDApi;
import com.romellfudi.ussdlibrary.USSDController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

    DaoViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getActivity().getApplicationContext()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        new PermissionService(getActivity()).request(callback);
        mViewModel = ViewModelProviders.of(getActivity()).get(DaoViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final ContentOp1Binding binding = DataBindingUtil.inflate(inflater, R.layout.content_op1, container, false);
        Objects.requireNonNull(getActivity());
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(getActivity());
        setHasOptionsMenu(false);

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phone.getText().toString().trim();
                binding.phone.setText(phone);
                binding.result.setText("");
                ussdApi = USSDController.getInstance(getActivity());
                ussdApi.callUSSDInvoke(phone, map, new USSDController.CallbackInvoke() {
                    @Override
                    public void responseInvoke(String message) {
                        Timber.d(message);
                        binding.result.append("\n-\n" + message);
                        // first option list - select option 1
                        ussdApi.send("1", new USSDController.CallbackMessage() {
                            @Override
                            public void responseMessage(String message) {
                                Timber.i(message);
                                binding.result.append("\n-\n" + message);
                                // second option list - select option 2
                                ussdApi.send("2", new USSDController.CallbackMessage() {
                                    @Override
                                    public void responseMessage(String message) {
                                        Timber.i(message);
                                        binding.result.append("\n-\n" + message);
                                        // second option list - select option 1
                                        ussdApi.send("1", new USSDController.CallbackMessage() {
                                            @Override
                                            public void responseMessage(String message) {
                                                Timber.i(message);
                                                binding.result.append("\n-\n" + message);
                                            }
                                        });
                                    }
                                });
                            }
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
            }
        });

        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (USSDController.verifyOverLay(getActivity())) {
                    final Intent svc = new Intent(getActivity(), OverlayShowingService.class);
                    svc.putExtra(OverlayShowingService.EXTRA, "PROCESANDO");
                    getActivity().startService(svc);
                    Timber.i("START OVERLAY DIALOG");
                    String phoneNumber = binding.phone.getText().toString().trim();
                    ussdApi = USSDController.getInstance(getActivity());
                    binding.result.setText("");
                    ussdApi.callUSSDOverlayInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
                        @Override
                        public void responseInvoke(String message) {
                            Timber.i(message);
                            binding.result.append("\n-\n" + message);
                            // first option list - select option 1
                            ussdApi.send("1", new USSDController.CallbackMessage() {
                                @Override
                                public void responseMessage(String message) {
                                    Timber.i(message);
                                    binding.result.append("\n-\n" + message);
                                    // second option list - select option 2
                                    ussdApi.send("2", new USSDController.CallbackMessage() {
                                        @Override
                                        public void responseMessage(String message) {
                                            Timber.i(message);
                                            binding.result.append("\n-\n" + message);
                                            // second option list - select option 1
                                            ussdApi.send("1", new USSDController.CallbackMessage() {
                                                @Override
                                                public void responseMessage(String message) {
                                                    Timber.i(message);
                                                    binding.result.append("\n-\n" + message);
                                                    getActivity().stopService(svc);
                                                    Timber.i("STOP OVERLAY DIALOG");
                                                    Timber.i("successful");
                                                }
                                            });
                                        }
                                    });
                                }
                            });
//                            ussdApi.cancel();
                        }

                        @Override
                        public void over(String message) {
                            Timber.i(message);
                            binding.result.append("\n-\n" + message);
                            getActivity().stopService(svc);
                            Timber.i("STOP OVERLAY DIALOG");
                        }
                    });
                }
            }
        });

        binding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (USSDController.verifyOverLay(getActivity())) {
                    final Intent svc = new Intent(getActivity(), SplashLoadingService.class);
                    getActivity().startService(svc);
                    Timber.i("START SPLASH DIALOG");
                    String phoneNumber = binding.phone.getText().toString().trim();
                    binding.result.setText("");
                    ussdApi.callUSSDOverlayInvoke(phoneNumber, map, new USSDController.CallbackInvoke() {
                        @Override
                        public void responseInvoke(String message) {
                            Timber.i(message);
                            binding.result.append("\n-\n" + message);
                            // first option list - select option 1
                            ussdApi.send("1", new USSDController.CallbackMessage() {
                                @Override
                                public void responseMessage(String message) {
                                    Timber.i(message);
                                    binding.result.append("\n-\n" + message);
                                    // second option list - select option 2
                                    ussdApi.send("2", new USSDController.CallbackMessage() {
                                        @Override
                                        public void responseMessage(String message) {
                                            Timber.i(message);
                                            binding.result.append("\n-\n" + message);
                                            // second option list - select option 1
                                            ussdApi.send("1", new USSDController.CallbackMessage() {
                                                @Override
                                                public void responseMessage(String message) {
                                                    Timber.i(message);
                                                    binding.result.append("\n-\n" + message);
                                                    getActivity().stopService(svc);
                                                    Timber.i("STOP OVERLAY DIALOG");
                                                    Timber.i("successful");
                                                }
                                            });
                                        }
                                    });
                                }
                            });
//                            ussdApi.cancel();
                        }

                        @Override
                        public void over(String message) {
                            Timber.i(message);
                            binding.result.append("\n-\n" + message);
                            getActivity().stopService(svc);
                            Timber.i("STOP OVERLAY DIALOG");
                        }
                    });
                }
            }
        });

        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USSDController.verifyAccesibilityAccess(getActivity());
            }
        });

        return binding.getRoot();
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
        callback.handler(permissions, grantResults);
    }
}

