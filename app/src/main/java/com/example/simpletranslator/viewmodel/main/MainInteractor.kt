package com.example.simpletranslator.viewmodel.main

import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.model.repository.RepositoryLocal
import com.example.simpletranslator.viewmodel.Interactor

class MainInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState

        if (fromRemoteSource) {
            appState = AppState.Success(remoteRepository.getData(word))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(repositoryLocal.getData(word))
        }

        return appState
    }
}