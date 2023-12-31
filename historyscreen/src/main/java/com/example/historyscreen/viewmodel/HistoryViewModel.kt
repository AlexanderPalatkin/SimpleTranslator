package com.example.historyscreen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.viewmodel.BaseViewModel
import com.example.historyscreen.interactor.HistoryInteractor
import com.example.historyscreen.repository.HistoryRepository
import com.example.model.AppState
import com.example.repository.parseLocalSearchResults
import com.example.repository.room.HistoryEntity
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val interactor: HistoryInteractor,
    private val repository: HistoryRepository<HistoryEntity>
) :
    BaseViewModel<AppState>() {

    private val liveDataForViewToObserve: MutableLiveData<AppState> = MutableLiveData()

    private val liveDataSearchWord: MutableLiveData<HistoryEntity> = MutableLiveData()

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)

        super.onCleared()
    }

    fun subscribeToSearchWord(): LiveData<HistoryEntity> {

        return liveDataSearchWord
    }

    fun subscribe(): LiveData<AppState> {

        return liveDataForViewToObserve
    }

    fun getDataByWord(word: String) {
        viewModelCoroutineScope.launch {
            liveDataSearchWord.postValue(repository.getDataById(word))
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = AppState.Loading(null)

        cancelJob()

        viewModelCoroutineScope.launch { startInteractor(word, isOnline) }
    }

    private suspend fun startInteractor(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.postValue(
            parseLocalSearchResults(
                interactor.getData(
                    word,
                    isOnline
                )
            )
        )
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }
}