package com.example.simpletranslator.viewmodel

import androidx.lifecycle.ViewModel
import com.example.simpletranslator.model.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState>
    : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable -> handleError(throwable) }
    )

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        super.onCleared()

        cancelJob()
    }

    abstract fun getData(word: String, isOnline: Boolean)

    abstract fun handleError(error: Throwable)
}