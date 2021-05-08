package com.romellfudi.ussd.main.entity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Main ViewModel for the calls
 *
 * @version 1.0.a
 * @autor Romell Domínguez (@romellfudi)
 * @date 3/8/21
 */
class CallViewModel : ViewModel() {

    var number = MutableLiveData<String>()
    val result = MutableLiveData<String>()
    private val _dialUpType = MutableLiveData<String>()
    val dialUpType: LiveData<String> = _dialUpType

    init {
        number.value = "*515#"
        result.value = "Empty"
    }

    fun setDialUpType(value:String){
        _dialUpType.value = value
    }

    fun hasNoFlavorSet(): Boolean {
        return _dialUpType.value.isNullOrEmpty()
    }
}