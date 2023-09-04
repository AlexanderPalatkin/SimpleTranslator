package com.example.simpletranslator.model.repository

import com.example.simpletranslator.model.data.AppState

interface RepositoryLocal<T>: Repository<T> {

    suspend fun saveToDB(appState: AppState)
}