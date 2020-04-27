package com.romellfudi.ussd.main.interactor

import io.reactivex.Observable


/**
 * @autor Romell Domínguez
 * @date 2020-04-27
 * @version 1.0
 */
interface MainFragmentMVPInteractor {

    fun seedQuestions(): Observable<Boolean>
    fun getQuestion(): Observable<List<Boolean>>
}