package com.example.simpletranslator.model.datasource

interface DataSource<T> {
    suspend fun getData(word: String): T
}