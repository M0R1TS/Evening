package ru.devsokovix.evening.utils

import ru.devsokovix.evening.data.entity.Film
import ru.devsokovix.remote_module.entity.TmdbFilm

object Converter {
    fun convertApiListToDTOList(list: List<ru.devsokovix.remote_module.entity.TmdbFilm>?): List<Film> {
        val result = mutableListOf<Film>()
        list?.forEach {
            result.add(Film(
                title = it.title,
                poster = it.posterPath,
                description = it.overview,
                rating = it.voteAverage,
                isInFavorites = false
            ))
        }
        return result
    }
}