package com.example.simpletranslator.model.repository

import com.example.repository.room.HistoryDao
import com.example.repository.room.HistoryEntity

class HistoryRepositoryImplementation(private val historyDao: HistoryDao) :
    HistoryRepository<HistoryEntity> {

    override suspend fun getDataById(data: String): HistoryEntity {
        return historyDao.getDataByWord(data)
    }
}