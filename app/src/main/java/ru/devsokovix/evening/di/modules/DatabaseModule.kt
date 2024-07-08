package ru.devsokovix.evening.di.modules

import dagger.Module
import dagger.Provides
import ru.devsokovix.evening.data.MainRepository
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideRepository() = MainRepository()
}