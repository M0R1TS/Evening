package ru.devsokovix.evening.domain

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.data.PreferenceProvider
import ru.devsokovix.evening.data.TmdbApi
import ru.devsokovix.evening.data.entity.Film
import ru.devsokovix.evening.data.entity.TmdbResultsDto
import ru.devsokovix.evening.utils.API
import ru.devsokovix.evening.utils.Converter

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider,
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun getFilmsFromApi(page: Int) {
        // Показываем ProgressBar
        progressBarState.onNext(true)
        // Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), "d645fd0126994d7f9c9946a1e942f5c5", "ru-RU", page).enqueue(
            object : Callback<TmdbResultsDto> {
                override fun onResponse(
                    call: Call<TmdbResultsDto>,
                    response: Response<TmdbResultsDto>,
                ) {
                    val list = Converter.convertApiListToDTOList(response.body()?.tmdbFilms)
                    // Кладем фильмы в бд
                    // В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                    Completable
                        .fromSingle<List<Film>> {
                            repo.putToDb(list)
                        }.subscribeOn(Schedulers.io())
                        .subscribe()
                    progressBarState.onNext(false)
                }

                override fun onFailure(
                    call: Call<TmdbResultsDto>,
                    t: Throwable,
                ) {
                    // В случае провала выключаем ProgressBar
                    progressBarState.onNext(false)
                }
            },
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
