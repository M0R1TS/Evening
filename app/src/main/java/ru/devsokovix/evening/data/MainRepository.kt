package ru.devsokovix.evening.data

import android.content.ContentValues
import android.database.Cursor
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.devsokovix.evening.data.dao.FilmDao
import ru.devsokovix.evening.data.db.DatabaseHelper
import ru.devsokovix.evening.data.entity.Film
import java.util.concurrent.Executors

class MainRepository(private val filmDao: FilmDao) {
    private lateinit var scope: CoroutineScope
    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Flow<List<Film>> = filmDao.getCachedFilms()

    //Очистка кэша
    fun clearCache(){
        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                val list = filmDao.getCachedFilms()
                list.collect {
                    withContext(Dispatchers.Main) {
                        filmDao.deleteDB(it)
                    }
                }
            }
        }
    }
    //Очистить кэш от фильмов с рейтингом ниже 8.0
    fun clearInCacheBadFilms(){
        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                val list = filmDao.getCachedFilmsGood(7.99, 10.00)
                list.collect {
                    withContext(Dispatchers.Main) {
                        it.forEach {
                            filmDao.deleteFilm(it)
                        }
                    }
                }
            }
        }
    }
}