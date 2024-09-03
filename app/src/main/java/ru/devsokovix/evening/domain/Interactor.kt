package ru.devsokovix.evening.domain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.devsokovix.evening.data.MainRepository
import ru.devsokovix.evening.data.PreferenceProvider
import ru.devsokovix.evening.data.TmdbApi
import ru.devsokovix.evening.data.entity.Film
import ru.devsokovix.evening.data.entity.TmdbResultsDto
import ru.devsokovix.evening.databinding.FragmentHomeBinding
import ru.devsokovix.evening.utils.API
import ru.devsokovix.evening.utils.AnimationHelper
import ru.devsokovix.evening.utils.AutoDisposable
import ru.devsokovix.evening.utils.Converter
import ru.devsokovix.evening.utils.addTo
import ru.devsokovix.evening.view.MainActivity
import ru.devsokovix.evening.view.rv_adapters.FilmListRecyclerAdapter
import ru.devsokovix.evening.view.rv_adapters.TopSpacingItemDecoration
import ru.devsokovix.evening.viewmodel.HomeFragmentViewModel
import java.util.Locale
import java.util.concurrent.TimeUnit

class Interactor(private val repo: MainRepository, private val retrofitService: TmdbApi,
private val preferences: PreferenceProvider
) {
    var progressBarState: BehaviorSubject<Boolean> = BehaviorSubject.create()
    //В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, которую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(page: Int) {
        //Показываем ProgressBar
        progressBarState.onNext(true)

        //Метод getDefaultCategoryFromPreferences() будет получать при каждом запросе нужный нам список фильмов
        retrofitService.getFilms(getDefaultCategoryFromPreferences(), API.KEY, "ru-RU", page).enqueue(object : Callback<TmdbResultsDto> {
            override fun onResponse(call: Call<TmdbResultsDto>, response: Response<TmdbResultsDto>) {
                val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                //Кладем фильмы в бд
                //В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                Completable.fromSingle<List<Film>> {
                    repo.putToDb(list)
                }
                    .subscribeOn(Schedulers.io())
                    .subscribe()
                progressBarState.onNext(false)
            }

            override fun onFailure(call: Call<TmdbResultsDto>, t: Throwable) {
                //В случае провала выключаем ProgressBar
                progressBarState.onNext(false)
            }
        })
    }
    //Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }
    //Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): Observable<List<Film>> = repo.getAllFromDB()

    fun clearCache() = repo.clearCache()

    fun clearInCacheBadFilms() = repo.clearInCacheBadFilms()

    fun getSearchResultFromApi(search: String): Observable<List<Film>> = retrofitService.getFilmFromSearch(API.KEY, "ru-RU", search, 1)
        .map {
            Converter.convertApiListToDtoList(it.tmdbFilms)
        }
}