package com.example.simpletranslator.presenter

import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.view.base.View

interface Presenter<T: AppState, V: View> {
    fun attachView(view: V)
    fun detachView(view: V)
    fun getData(word: String, isOnline: Boolean)
}