package com.example.simpletranslator.model.repository

interface HistoryRepository<T> {

    suspend fun getDataById(data: String): T
}