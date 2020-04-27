package com.romellfudi.ussd.main.interactor

import android.content.Context
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
class MainFragmentInteractor @Inject constructor(private val mContext: Context) : MainFragmentMVPInteractor {

    override fun seedQuestions(): Observable<Boolean> {
        return Observable.fromCallable { Data().boolean }
    }

    override fun getQuestion(): Observable<List<Boolean>> {
        return Observable.fromCallable { listOf(Data().boolean, Data().boolean) }
    }

    class Data {
        var boolean: Boolean = false

    }

}