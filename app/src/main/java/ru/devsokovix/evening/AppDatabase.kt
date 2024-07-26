package ru.devsokovix.evening

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.devsokovix.evening.data.dao.FilmDao
import ru.devsokovix.evening.data.entity.Film

@Database(entities = [Film::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
}