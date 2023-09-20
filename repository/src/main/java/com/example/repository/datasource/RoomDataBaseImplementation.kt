package com.example.repository.datasource

import com.example.model.AppState
import com.example.model.dto.SearchResultDto
import com.example.repository.room.HistoryDao
import com.example.repository.convertDataModelSuccessToEntity
import com.example.repository.mapHistoryEntityToSearchResult

class RoomDataBaseImplementation(private val historyDao: HistoryDao) :
    DataSourceLocal<List<SearchResultDto>> {

    override suspend fun getData(word: String): List<SearchResultDto> {
        return mapHistoryEntityToSearchResult(historyDao.all())
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}