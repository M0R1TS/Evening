package ru.devsokovix.evening.domain

import ru.devsokovix.evening.data.MainRepository

class Interactor(private val repo: MainRepository) {
    fun getFilmsDB(): List<Film> = repo.filmsDataBase
}