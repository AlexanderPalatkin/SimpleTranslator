package com.example.historyscreen.repository

interface HistoryRepository<T> {

    suspend fun getDataById(data: String): T
}