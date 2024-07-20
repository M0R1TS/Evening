package ru.devsokovix.evening.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.data.db.DatabaseHelper
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context) = DatabaseHelper(context)

    @Provides
    @Singleton
    fun provideRepository(databaseHelper: DatabaseHelper) = MainRepository(databaseHelper)
}