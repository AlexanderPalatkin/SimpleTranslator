package com.example.simpletranslator.viewmodel.history

import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.model.repository.RepositoryLocal
import com.example.simpletranslator.viewmodel.Interactor

class HistoryInteractor(
    private val repositoryRemote: Repository<List<DataModel>>,
    private val repositoryLocal: RepositoryLocal<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return AppState.Success(
            if (fromRemoteSource) {
                repositoryRemote
            } else {
                repositoryLocal
            }.getData(word)
        )
    }
}