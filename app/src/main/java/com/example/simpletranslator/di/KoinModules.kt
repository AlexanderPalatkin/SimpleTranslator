package com.example.simpletranslator.di

import androidx.room.Room
import com.example.model.data.DataModel
import com.example.simpletranslator.model.datasource.RetrofitImplementation
import com.example.simpletranslator.model.datasource.RoomDataBaseImplementation
import com.example.simpletranslator.model.repository.*
import com.example.simpletranslator.model.room.HistoryDataBase
import com.example.simpletranslator.model.room.HistoryEntity
import com.example.simpletranslator.viewmodel.history.HistoryInteractor
import com.example.simpletranslator.viewmodel.history.HistoryViewModel
import com.example.simpletranslator.viewmodel.main.MainInteractor
import com.example.simpletranslator.viewmodel.main.MainViewModel
import org.koin.dsl.module

val application = module {

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }

    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<DataModel>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }

    single<HistoryRepository<HistoryEntity>>{
        HistoryRepositoryImplementation(get())
    }
}

val mainScreen = module {
    factory { MainInteractor(get(), get()) }
    factory { MainViewModel(get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get(), get()) }
    factory { HistoryInteractor(get(), get()) }
}