package com.example.simpletranslator.di

import com.example.simpletranslator.model.data.DataModel
import com.example.simpletranslator.model.repository.Repository
import com.example.simpletranslator.view.main.MainInteractor
import dagger.Module
import javax.inject.Named

@Module
class InteractorModule {

    internal fun provideInteractor(@Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
    @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>) =
        MainInteractor(repositoryRemote, repositoryLocal)
}