/*
 * Copyright (c) 2020. BoostTag E.I.R.L. Romell D.Z.
 * All rights reserved
 * porfile.romellfudi.com
 */

package com.romellfudi.ussdlibrary;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doAnswer;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * @version 1.0
 * @autor Romell Domínguez
 * @date 10/4/18
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({USSDController.class,USSDService.class, Uri.class})
public class USSDApiTest {

    @Mock
    Activity activity;

    @InjectMocks
    USSDController ussdController = USSDController.getInstance(activity);

    @InjectMocks
    USSDService ussdService = new USSDService();

    @Mock
    ApplicationInfo applicationInfo;

    @Mock
    USSDController.CallbackInvoke callbackInvoke;

    @Mock
    USSDController.CallbackMessage callbackMessage;

    @Mock
    Uri uri;

    @Mock
    String string;

    @Mock
    AccessibilityEvent accessibilityEvent;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Mock
    USSDInterface ussdInterface;

    @Test
    public void verifyAccesibilityAccessTest() {
        when(activity.getApplicationInfo()).thenReturn(applicationInfo);
        applicationInfo.nonLocalizedLabel = getClass().getPackage().toString();
        USSDController.verifyAccesibilityAccess(activity);
    }

    @Test
    public void callUSSDInvokeTest() {
        HashMap<String, HashSet<String>> map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
        mockStatic(USSDController.class);
        mockStatic(Uri.class);
        when(USSDController.verifyAccesibilityAccess(any(Activity.class))).thenReturn(true);
        when(Uri.decode(any(String.class))).thenReturn(string);
        when(Uri.parse(any(String.class))).thenReturn(uri);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ussdService.onAccessibilityEvent(accessibilityEvent);
                return null;
            }
        }).when(activity).startActivity(any(Intent.class));
        when(accessibilityEvent.getClassName()).thenReturn("amigo.app.AmigoAlertDialog");
        List<CharSequence> texts = new ArrayList<CharSequence>(){};

        String MESSAGE = "waiting";
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        ussdController.callUSSDInvoke("*1#", map, callbackInvoke);
        verify(callbackInvoke).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));

        MESSAGE = "problem UUID";
        texts.remove(0);
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        ussdController.callUSSDInvoke("*1#", map, callbackInvoke);
        verify(callbackInvoke,times(2)).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
    }

    @Test
    public void callUSSDLoginWithNotInputText() {
        HashMap<String, HashSet<String>> map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
        mockStatic(USSDController.class);
        mockStatic(USSDService.class);
        mockStatic(Uri.class);
        when(USSDController.verifyAccesibilityAccess(any(Activity.class))).thenReturn(true);
        when(Uri.decode(any(String.class))).thenReturn(string);
        when(Uri.parse(any(String.class))).thenReturn(uri);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ussdService.onAccessibilityEvent(accessibilityEvent);
                return null;
            }
        }).when(activity).startActivity(any(Intent.class));
        when(accessibilityEvent.getClassName()).thenReturn("amigo.app.AmigoAlertDialog");
        List<CharSequence> texts = new ArrayList<CharSequence>(){};

        String MESSAGE = "loading";
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        when(USSDService.notInputText(accessibilityEvent)).thenReturn(true);
        ussdController.callUSSDInvoke("*1#", map, callbackInvoke);
        verify(callbackInvoke,times(1)).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));

    }

    @Test
    public void callUSSDSendMultipleMessages() {
        HashMap<String, HashSet<String>> map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
        mockStatic(USSDController.class);
        mockStatic(USSDService.class);
        mockStatic(Uri.class);
        when(USSDController.verifyAccesibilityAccess(any(Activity.class))).thenReturn(true);
        when(Uri.decode(any(String.class))).thenReturn(string);
        when(Uri.parse(any(String.class))).thenReturn(uri);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ussdService.onAccessibilityEvent(accessibilityEvent);
                return null;
            }
        }).when(activity).startActivity(any(Intent.class));
        when(accessibilityEvent.getClassName()).thenReturn("amigo.app.AmigoAlertDialog");
        List<CharSequence> texts = new ArrayList<CharSequence>(){};

        String MESSAGE = "waiting";
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        ussdController.callUSSDInvoke("*1#", map, callbackInvoke);
        verify(callbackInvoke,times(1)).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ussdService.onAccessibilityEvent(accessibilityEvent);
                return null;
            }
        }).when(ussdInterface).sendData(any(String.class));
        MESSAGE = "Return a message from GATEWAY USSD";
        texts.remove(0);
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(1)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(2)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(3)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(4)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));

        MESSAGE = "Final Close dialog";
        texts.remove(0);
        texts.add(MESSAGE);
        when(USSDService.notInputText(accessibilityEvent)).thenReturn(true);
        ussdController.send("1",callbackMessage);
        verify(callbackInvoke,times(2)).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
    }

    @Test
    public void callUSSDOverlayInvokeMultipleMessages() {
        HashMap<String, HashSet<String>> map = new HashMap<>();
        map.put("KEY_LOGIN", new HashSet<>(Arrays.asList("espere", "waiting", "loading", "esperando")));
        map.put("KEY_ERROR", new HashSet<>(Arrays.asList("problema", "problem", "error", "null")));
        mockStatic(USSDController.class);
        mockStatic(USSDService.class);
        mockStatic(Uri.class);
        when(USSDController.verifyAccesibilityAccess(any(Activity.class))).thenReturn(true);
        when(USSDController.verifyOverLay(any(Activity.class))).thenReturn(true);
        when(Uri.decode(any(String.class))).thenReturn(string);
        when(Uri.parse(any(String.class))).thenReturn(uri);
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ussdService.onAccessibilityEvent(accessibilityEvent);
                return null;
            }
        }).when(activity).startActivity(any(Intent.class));
        when(accessibilityEvent.getClassName()).thenReturn("amigo.app.AmigoAlertDialog");
        List<CharSequence> texts = new ArrayList<CharSequence>(){};

        String MESSAGE = "waiting";
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        ussdController.callUSSDOverlayInvoke("*1#", map, callbackInvoke);
        verify(callbackInvoke,times(1)).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ussdService.onAccessibilityEvent(accessibilityEvent);
                return null;
            }
        }).when(ussdInterface).sendData(any(String.class));
        MESSAGE = "Return a message from GATEWAY USSD";
        texts.remove(0);
        texts.add(MESSAGE);
        when(accessibilityEvent.getText()).thenReturn(texts);
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(1)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(2)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(3)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
        ussdController.send("1",callbackMessage);
        verify(callbackMessage,times(4)).responseMessage(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));

        MESSAGE = "Final Close dialog";
        texts.remove(0);
        texts.add(MESSAGE);
        when(USSDService.notInputText(accessibilityEvent)).thenReturn(true);
        ussdController.send("1",callbackMessage);
        verify(callbackInvoke,times(2)).over(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue(), is(equalTo(MESSAGE)));
    }
}