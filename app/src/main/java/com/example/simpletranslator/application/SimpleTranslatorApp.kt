package com.example.simpletranslator.application

import android.app.Application
import com.example.simpletranslator.di.application
import com.example.simpletranslator.di.historyScreen
import com.example.simpletranslator.di.mainScreen
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