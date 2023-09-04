package com.example.simpletranslator.model.repository

import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.datasource.DataSource

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}