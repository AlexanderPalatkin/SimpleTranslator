package com.example.repository.datasource

import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.repository.room.HistoryDao
import com.example.repository.convertDataModelSuccessToEntity
import com.example.repository.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}