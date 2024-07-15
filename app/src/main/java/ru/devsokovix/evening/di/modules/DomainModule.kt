package ru.devsokovix.evening.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.devsokovix.evening.data.Interactor
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.data.PreferenceProvider
import ru.devsokovix.evening.mn_interface.TmdbApi
import javax.inject.Singleton

// Передаём контекст для SharedPreferences через конструктор
@Module
class DomainModule(
    val context: Context,
) {
    // Нам нужен контекст как-то провайдить, поэтому создаём такой метод
    @Provides
    fun provideContext() = context

    // Создаем экземляр SharedPreferences
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
