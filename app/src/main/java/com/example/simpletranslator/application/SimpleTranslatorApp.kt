package com.example.simpletranslator.application

import android.app.Application
import com.example.simpletranslator.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SimpleTranslatorApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}