package ru.devsokovix.evening.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import ru.devsokovix.evening.App
import ru.devsokovix.evening.data.Interactor
import ru.devsokovix.evening.domain.Film

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData: MutableLiveData<List<Film>> = MutableLiveData()
    private var page = 1

    // Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor

    init {
        getdata(1)
    }

    private fun getdata(page: Int) {
        interactor.getFilmsFromApi(
            page,
            object : ApiCallback {
                override fun onSuccess(films: List<Film>) {
                    filmsListLiveData.postValue(films)
                }

                override fun onFailure() {
                }
            },
        )
    }

    fun doPagination(
        visibleItemCount: Int,
        totalItemCount: Int,
        postVisibleItemCount: Int,
        recyclerView: RecyclerView
    ) {
        if ((visibleItemCount + postVisibleItemCount) >= totalItemCount) {
            recyclerView.postDelayed(
                Runnable {
                    // перемещаем пользователя на вверх
                    recyclerView.smoothScrollToPosition(0)

                    // задержка отсутствует
                },
                0,
            )
            getdata(++page)
        }
    }
}

interface ApiCallback {
    fun onSuccess(films: List<Film>)

    fun onFailure()
}
