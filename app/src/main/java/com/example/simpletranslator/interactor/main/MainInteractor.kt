package com.example.simpletranslator.interactor.main

import com.example.model.AppState
import com.example.model.dto.SearchResultDto
import com.example.repository.Repository
import com.example.repository.RepositoryLocal
import com.example.core.viewmodel.Interactor
import com.example.repository.mapSearchResultToResult

class MainInteractor(
    private val remoteRepository: Repository<List<SearchResultDto>>,
    private val repositoryLocal: RepositoryLocal<List<SearchResultDto>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        val appState: AppState

        if (fromRemoteSource) {
            appState = AppState.Success(mapSearchResultToResult(remoteRepository.getData(word)))
            repositoryLocal.saveToDB(appState)
        } else {
            appState = AppState.Success(mapSearchResultToResult(repositoryLocal.getData(word)))
        }

        return appState
    }
}