package ru.devsokovix.evening.mn_interface


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.devsokovix.evening.domain.TmdbResultsDto

interface TmdbApi {
    @GET("3/movie/popular")
    fun getFilms(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<TmdbResultsDto>
}