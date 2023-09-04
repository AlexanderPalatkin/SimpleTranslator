package com.example.simpletranslator.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.utils.parseLocalSearchResults
import com.example.simpletranslator.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class HistoryViewModel(private val interactor: HistoryInteractor) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)

        super.onCleared()
    }

    fun subscribe(): LiveData<AppState> {

        return liveDataForViewToObserve
    }

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.postValue(parseLocalSearchResults(interactor.getData(word, isOnline)))
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }
}