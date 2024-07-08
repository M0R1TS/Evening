package ru.devsokovix.evening.di.modules

import dagger.Module
import dagger.Provides
import ru.devsokovix.evening.data.Interactor
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.mn_interface.TmdbApi
import javax.inject.Singleton

@Module
class DomainModule {
    @Singleton
    @Provides
    fun provideInteractor(repository: MainRepository, tmdbApi: TmdbApi) = Interactor(repo = repository, retrofitService = tmdbApi)
}
