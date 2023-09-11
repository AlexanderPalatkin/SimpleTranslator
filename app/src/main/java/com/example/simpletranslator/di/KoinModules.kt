package com.example.simpletranslator.di

import androidx.room.Room
import com.example.historyscreen.repository.HistoryRepository
import com.example.historyscreen.repository.HistoryRepositoryImplementation
import com.example.historyscreen.interactor.HistoryInteractor
import com.example.historyscreen.viewmodel.HistoryViewModel
import com.example.model.data.DataModel
import com.example.repository.Repository
import com.example.repository.RepositoryImplementation
import com.example.repository.RepositoryImplementationLocal
import com.example.repository.RepositoryLocal
import com.example.repository.datasource.RetrofitImplementation
import com.example.repository.datasource.RoomDataBaseImplementation
import com.example.repository.room.HistoryDataBase
import com.example.repository.room.HistoryEntity
import com.example.simpletranslator.interactor.main.MainInteractor
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