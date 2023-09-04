package com.example.simpletranslator.model.repository

import com.example.simpletranslator.model.data.AppState
import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.datasource.DataSourceLocal

class RepositoryImplementationLocal(private val dataSource: DataSourceLocal<List<DataModel>>) :
    RepositoryLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }

    override suspend fun saveToDB(appState: AppState) {
        dataSource.saveToDB(appState)
    }
}