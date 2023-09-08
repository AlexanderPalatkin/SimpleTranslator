package com.example.simpletranslator.viewmodel.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.model.AppState
import com.example.simpletranslator.model.repository.HistoryRepository
import com.example.simpletranslator.model.room.HistoryEntity
import com.example.simpletranslator.utils.parseLocalSearchResults
import com.example.simpletranslator.viewmodel.BaseViewModel
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