package com.example.simpletranslator.model.repository

import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.datasource.DataSource
import io.reactivex.Observable

class RepositoryImplementation(private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> {
        return dataSource.getData(word)
    }
}