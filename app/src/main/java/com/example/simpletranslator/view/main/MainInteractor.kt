package com.example.simpletranslator.view.main

import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                remoteRepository
            } else {
                localRepository
            }.getData(word)
        )
    }
}