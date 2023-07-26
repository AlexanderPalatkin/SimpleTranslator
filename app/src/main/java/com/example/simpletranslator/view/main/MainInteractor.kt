package com.example.simpletranslator.view.main

import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.presenter.Interator
import io.reactivex.Observable

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interator<AppState> {
    override fun getData(word: String, fromRemoteSource: Boolean): Observable<AppState> {
        return if (fromRemoteSource) {
            remoteRepository.getData(word).map { AppState.Success(it) }
        } else {
            localRepository.getData(word).map { AppState.Success(it) }
        }
    }
}