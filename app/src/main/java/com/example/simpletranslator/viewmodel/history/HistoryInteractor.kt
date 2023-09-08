package com.example.simpletranslator.viewmodel.history

import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.model.repository.RepositoryLocal
import com.example.simpletranslator.viewmodel.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<com.example.model.AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): com.example.model.AppState {
        return com.example.model.AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}