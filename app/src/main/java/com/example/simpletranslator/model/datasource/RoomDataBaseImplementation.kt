package com.example.simpletranslator.model.datasource

import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.simpletranslator.model.room.HistoryDao
import com.example.simpletranslator.utils.convertDataModelSuccessToEntity
import com.example.simpletranslator.utils.mapHistoryEntityToSearchResult

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