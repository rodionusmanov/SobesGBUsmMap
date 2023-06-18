package com.example.sobesgbusmmap.filmApp.model.retrofit

import com.example.sobesgbusmmap.filmApp.model.Movie
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {
    @GET("title")
    fun getMovieList(): Call<MutableList<Movie>>
}