package com.example.sobesgbusmmap.filmApp.model.retrofit

import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject
import retrofit2.Call
import retrofit2.http.GET

interface FilmAPI {

    @GET("en/API/Top250Movies/k_zfsvu3kw")
    fun getTopFilmsList(): Call<TopFilmsDataTransferObject>
}