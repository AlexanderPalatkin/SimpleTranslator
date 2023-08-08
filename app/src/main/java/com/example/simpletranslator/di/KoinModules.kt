package com.example.simpletranslator.di

import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.datasource.RetrofitImplementation
import com.example.simpletranslator.model.datasource.RoomDataBaseImplementation
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.model.repository.RepositoryImplementation
import com.example.simpletranslator.view.main.MainInteractor
import com.example.simpletranslator.view.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val application = module {

    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) {
        RepositoryImplementation(
            RetrofitImplementation()
        )
    }

    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) {
        RepositoryImplementation(
            RoomDataBaseImplementation()
        )
    }
}

val mainScreen = module {
    single {
        MainInteractor(
            remoteRepository = get(named(NAME_REMOTE)),
            localRepository = get(named(NAME_LOCAL))
        )
    }

    viewModel {
        MainViewModel(interactor = get())
    }
}