package ru.devsokovix.evening.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.devsokovix.evening.App
import ru.devsokovix.evening.domain.Interactor
import ru.devsokovix.evening.data.entity.Film
import java.util.Calendar
import java.util.concurrent.Executors
import javax.inject.Inject

class HomeFragmentViewModel : ViewModel() {
    val showProgressBar: MutableLiveData<Boolean> = MutableLiveData()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor
    val filmsListLiveData : LiveData<List<Film>>
    init {
        App.instance.dagger.inject(this)
        filmsListLiveData = interactor.getFilmsFromDB()
        getFilms()
    }
    fun getFilms() {
        showProgressBar.postValue(true)
        val dataCur = Calendar.getInstance().timeInMillis
        val data = interactor.getDounloadTimeFromPreferences()
        if ((dataCur - data) > 600000){
            interactor.clearCache()
            interactor.getFilmsFromApi(1, object : ApiCallback {
                override fun onSuccess() {
                    showProgressBar.postValue(false)
                }

                override fun onFailure() {
                    Executors.newSingleThreadExecutor().execute {
                        showProgressBar.postValue(false)
                    }
                }
            })
        } else {
            Executors.newSingleThreadExecutor().execute {
                showProgressBar.postValue(false)
            }
        }
    }

    interface ApiCallback {
        fun onSuccess()
        fun onFailure()
    }
}

