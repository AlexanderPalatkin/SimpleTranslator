package com.example.simpletranslator.viewmodel

interface Interactor<T> {

    suspend fun getData(word: String, fromRemoteSource: Boolean): T
}