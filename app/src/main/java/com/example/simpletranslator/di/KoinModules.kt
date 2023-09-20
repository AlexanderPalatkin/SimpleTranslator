package com.example.simpletranslator.di

import androidx.room.Room
import com.example.historyscreen.interactor.HistoryInteractor
import com.example.historyscreen.repository.HistoryRepository
import com.example.historyscreen.repository.HistoryRepositoryImplementation
import com.example.historyscreen.view.HistoryActivity
import com.example.historyscreen.viewmodel.HistoryViewModel
import com.example.model.dto.SearchResultDto
import com.example.repository.Repository
import com.example.repository.RepositoryImplementation
import com.example.repository.RepositoryImplementationLocal
import com.example.repository.RepositoryLocal
import com.example.repository.datasource.RetrofitImplementation
import com.example.repository.datasource.RoomDataBaseImplementation
import com.example.repository.room.HistoryDataBase
import com.example.repository.room.HistoryEntity
import com.example.simpletranslator.interactor.main.MainInteractor
import com.example.simpletranslator.view.main.MainActivity
import com.example.simpletranslator.viewmodel.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }

    single { get<HistoryDataBase>().historyDao() }

    single<Repository<List<SearchResultDto>>> {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single<RepositoryLocal<List<SearchResultDto>>> {
        RepositoryImplementationLocal(
            RoomDataBaseImplementation(get())
        )
    }

    single<HistoryRepository<HistoryEntity>>{
        HistoryRepositoryImplementation(get())
    }
}

val mainScreen = module {
    scope(named<MainActivity>()) {
        scoped {
            MainInteractor(get(), get())
        }
        viewModel {
            MainViewModel(get())
        }
    }
}

val historyScreen = module {
    scope(named<HistoryActivity>()) {
        scoped {
            HistoryInteractor(get(), get())
        }
        viewModel {
            HistoryViewModel(get(), get())
        }
    }
}