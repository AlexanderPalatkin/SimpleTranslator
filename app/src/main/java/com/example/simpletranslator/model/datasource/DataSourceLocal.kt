package com.example.simpletranslator.model.datasource

import com.example.simpletranslator.model.data.DataModel
import io.reactivex.Observable

class DataSourceLocal(private val remoteProvider: RoomDataBaseImplementation =
                          RoomDataBaseImplementation()) :
    DataSource<List<DataModel>> {
    override fun getData(word: String): Observable<List<DataModel>> =
        remoteProvider.getData(word)
}