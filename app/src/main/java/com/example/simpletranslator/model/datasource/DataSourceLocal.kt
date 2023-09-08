package com.example.simpletranslator.model.datasource

import com.example.model.AppState

interface DataSourceLocal<T>: DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}