package ru.devsokovix.evening.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.devsokovix.evening.data.Interactor
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.data.PreferenceProvider
import ru.devsokovix.evening.mn_interface.TmdbApi
import javax.inject.Singleton

// Передаем контекст для SharedPreferences через конструктор
@Module
class DomainModule(
    val context: Context,
) {
    // Нам нужно контекст как-то провайдить, поэтому создаем такой метод
    @Provides
    fun provideContext() = context

    // Создаем экземпляр SharedPreferences
    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(
        repository: MainRepository,
        tmdbApi: TmdbApi,
        preferenceProvider: PreferenceProvider,
    ) = Interactor(repo = repository, retrofitService = tmdbApi, preferences = preferenceProvider)
}
