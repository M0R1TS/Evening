package ru.devsokovix.evening.data

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.devsokovix.evening.data.dao.FilmDao
import ru.devsokovix.evening.data.entity.Film
import ru.devsokovix.evening.utils.AutoDisposable
import ru.devsokovix.evening.utils.addTo

class MainRepository(private val filmDao: FilmDao) {
    private val autoDisposable = AutoDisposable()
    fun putToDb(films: List<Film>) {
        //Запросы в БД должны быть в отдельном потоке
        filmDao.insertAll(films)
    }

    fun getAllFromDB(): Observable<List<Film>> = filmDao.getCachedFilms()

    //Очистка кэша
    fun clearCache(){

        val cachedFilms = filmDao.getCachedFilms()
        cachedFilms.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                filmDao.deleteDB(list)
            }
            .addTo(autoDisposable)
    }
    //Очистить кэш от фильмов с рейтингом ниже 8.0
    fun clearInCacheBadFilms(){
        val cachedFilms = filmDao.getCachedFilmsGood(0.0, 7.99)
        cachedFilms.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                list.forEach {
                    filmDao.deleteFilm(it)
                }
            }
            .addTo(autoDisposable)
    }
}