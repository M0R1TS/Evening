package ru.devsokovix.evening.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.devsokovix.evening.App
import ru.devsokovix.evening.domain.Interactor
import ru.devsokovix.evening.data.entity.Film
import java.util.Calendar
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData = MutableLiveData<List<Film>>()
    init {
        App.instance.dagger.inject(this)
        getFilms()
    }
    fun getFilms() {
        val dataCur = Calendar.getInstance().timeInMillis
        val data = interactor.getDounloadTimeFromPreferences()
        if ((dataCur - data) > 600000){
            interactor.clearCache()
            interactor.getFilmsFromApi(1, object : ApiCallback {
                override fun onSuccess(films: List<Film>) {
                    filmsListLiveData.postValue(films)
                }

                override fun onFailure() {
                    Executors.newSingleThreadExecutor().execute {
                        filmsListLiveData.postValue(interactor.getFilmsFromDB())
                    }
                }
            })
        } else {
            Executors.newSingleThreadExecutor().execute {
                filmsListLiveData.postValue(interactor.getFilmsFromDB())
            }
        }
    }

    interface ApiCallback {
        fun onSuccess(films: List<Film>)
        fun onFailure()
    }
}
