package com.example.simpletranslator.view.main

import androidx.lifecycle.LiveData
import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.utils.parseSearchResults
import com.example.simpletranslator.viewmodel.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val interactor: MainInteractor
) : BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: LiveData<AppState> = _liveDataForViewToObserve

    fun subscribe(): LiveData<AppState> {
        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        _liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            _liveDataForViewToObserve.postValue(
                parseSearchResults(
                    interactor.getData(
                        word,
                        isOnline
                    )
                )
            )
        }

    override fun handlerError(error: Throwable) {
        _liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        _liveDataForViewToObserve.value = AppState.Success(null)
        super.onCleared()
    }
}