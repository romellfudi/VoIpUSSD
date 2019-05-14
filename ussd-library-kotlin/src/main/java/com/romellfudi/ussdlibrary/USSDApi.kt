package com.romellfudi.ussdlibrary

import java.util.*

/**
 *
 * @author Romell Dominguez
 * @version 1.1.i 2019/04/18
 * @since 1.1.i
 */
interface USSDApi {
    fun send(text: String, callbackMessage: (String) -> Unit)
    fun callUSSDInvoke(ussdPhoneNumber: String, map: HashMap<String, HashSet<String>>,
                       callbackInvoke: USSDController.CallbackInvoke)

    fun callUSSDInvoke(ussdPhoneNumber: String, simSlot: Int, map: HashMap<String, HashSet<String>>,
                       callbackInvoke: USSDController.CallbackInvoke)

    fun callUSSDOverlayInvoke(ussdPhoneNumber: String, map: HashMap<String, HashSet<String>>,
                              callbackInvoke: USSDController.CallbackInvoke)

    fun callUSSDOverlayInvoke(ussdPhoneNumber: String, simSlot: Int, map: HashMap<String, HashSet<String>>,
                              callbackInvoke: USSDController.CallbackInvoke)
}
