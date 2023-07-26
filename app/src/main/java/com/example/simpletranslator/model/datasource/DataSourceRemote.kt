package com.example.simpletranslator.model.datasource

import com.example.simpletranslator.model.data.DataModel
import io.reactivex.Observable

class DataSourceRemote(private val remoteProvider: RetrofitImplementation =
                           RetrofitImplementation()) :
    DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}