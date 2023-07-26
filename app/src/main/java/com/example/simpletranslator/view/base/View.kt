package com.example.simpletranslator.view.base

import com.example.simpletranslator.model.data.AppState

interface View {
    fun renderData(appState: AppState)
}