package ru.devsokovix.evening.mn_interface

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface  {
    @GET("api/users")
    fun getUsers(
        @Query("page") page: Int
    ): Call<UsersData>
}