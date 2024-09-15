package ru.devsokovix.evening.domain

import android.annotation.SuppressLint
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.data.PreferenceProvider
import ru.devsokovix.remote_module.TmdbApi
import ru.devsokovix.evening.data.entity.Film
import ru.devsokovix.evening.utils.API
import ru.devsokovix.remote_module.entity.TmdbResultsDto
import ru.devsokovix.evening.utils.Converter

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: ru.devsokovix.remote_module.TmdbApi,
    private val preferences: PreferenceProvider,
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    @SuppressLint("CheckResult")
    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)
        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page)
            .subscribeOn(Schedulers.io())
            .map {
                Converter.convertApiListToDTOList(it.tmdbFilms)
            }
            .subscribeBy(
                onError = {
                    progressBarState.onNext(false)
                },
                onNext = {
                    progressBarState.onNext(false)
                    repo.putToDb(it)
                }
            )
    }

    fun getSearchResultFromApi(search: String): Observable<List<Film>> =
        retrofitService
            .getFilmFromSearch("d645fd0126994d7f9c9946a1e942f5c5", "ru-RU", search, 1)
            .map {
                Converter.convertApiListToDTOList(it.tmdbFilms)
            }

    // Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    // Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.geDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()
}
