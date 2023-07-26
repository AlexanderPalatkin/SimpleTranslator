package com.example.simpletranslator.presenter

import io.reactivex.Observable

interface Interator<T> {
    fun getData(word: String, fromRemoteSource: Boolean): Observable<T>
}