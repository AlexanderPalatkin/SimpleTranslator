package com.example.simpletranslator.model.repository

interface Repository<T> {
    suspend fun getData(word: String): T
}