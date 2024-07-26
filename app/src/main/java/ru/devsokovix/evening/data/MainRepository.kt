package ru.devsokovix.evening.data

import android.content.ContentValues
import android.database.Cursor
import ru.devsokovix.evening.data.dao.FilmDao
import ru.devsokovix.evening.data.db.DatabaseHelper
import ru.devsokovix.evening.data.entity.Film
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {
    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            filmDao.insertAll(films)
        }
    }

    fun getAllFromDB(): List<Film> {
        return filmDao.getCachedFilms()
    }
    //Очистка кэша
    fun clearCache(){
        Executors.newSingleThreadExecutor().execute {
            val list = filmDao.getCachedFilms()
            filmDao.deleteDB(list)
        }
    }
    //Очистить кэш от фильмов с рейтингом ниже 8.0
    fun clearInCacheBadFilms(){
        Executors.newSingleThreadExecutor().execute {
            val list = filmDao.getCachedFilmsGood(0.0, 7.99)
            list.forEach{
                filmDao.deleteFilm(it)
            }
        }
    }
}