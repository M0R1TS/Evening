package ru.devsokovix.evening.data

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.devsokovix.evening.domain.Film
import ru.devsokovix.evening.domain.TmdbResultsDto
import ru.devsokovix.evening.mn_interface.TmdbApi
import ru.devsokovix.evening.utils.API
import ru.devsokovix.evening.utils.Converter
import ru.devsokovix.evening.viewmodel.HomeFragmentViewModel

class Interactor(
    private val repo: MainRepository,
    private val retrofitService: TmdbApi,
    private val preferences: PreferenceProvider,
) {
    // В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    // и страницу, которую нужно загрузить (это для пагинации)
    fun getFilmsFromApi(
        page: Int,
        callback: HomeFragmentViewModel.ApiCallback,
    ) {
        // Метод getDefaultCategoryFromPreferences() будет нам получать при каждом запросе нужный нам список фильмов
        retrofitService
            .getFilms(
                getDefaultCategoryFromPreferences(),
                API.KEY,
                "ru-RU",
                page,
            ).enqueue(
                object : Callback<TmdbResultsDto> {
                    override fun onResponse(
                        call: Call<TmdbResultsDto>,
                        response: Response<TmdbResultsDto>,
                    ) {
                        // При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
                        val list = Converter.convertApiListToDtoList(response.body()?.tmdbFilms)
                        // Кладем фильмы в бд
                        list.forEach {
                            repo.putToDb(film = it)
                        }
                        callback.onSuccess(list)
                    }

                    override fun onFailure(
                        call: Call<TmdbResultsDto>,
                        t: Throwable,
                    ) {
                        // В случае провала вызываем другой метод коллбека
                        callback.onFailure()
                    }
                },
            )
    }

    // Метод для сохранения настроек
    fun saveDefaultCategoryToPreferences(category: String) {
        preferences.saveDefaultCategory(category)
    }

    // Метод для получения настроек
    fun getDefaultCategoryFromPreferences() = preferences.getDefaultCategory()

    fun getFilmsFromDB(): List<Film> = repo.getAllFromDB()

    fun clearCache() = repo.clearCache()

    fun clearInCacheBadFilms() = repo.clearInCacheBadFilms()
}
