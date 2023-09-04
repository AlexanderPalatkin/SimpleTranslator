package com.example.simpletranslator.model.repository

import com.example.simpletranslator.model.room.HistoryDao
import com.example.simpletranslator.model.room.HistoryEntity

class HistoryRepositoryImplementation(private val historyDao: HistoryDao) :
    HistoryRepository<HistoryEntity> {

    override suspend fun getDataById(data: String): HistoryEntity {
        return historyDao.getDataByWord(data)
    }
}