package com.example.simpletranslator.model.datasource

import com.example.simpletranslator.model.data.AppState

interface DataSourceLocal<T>: DataSource<T> {

    suspend fun saveToDB(appState: AppState)
}