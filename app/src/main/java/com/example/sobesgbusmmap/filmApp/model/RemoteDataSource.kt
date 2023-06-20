package com.example.sobesgbusmmap.filmApp.model

import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject
import com.example.sobesgbusmmap.filmApp.model.retrofit.FilmAPI
import com.example.sobesgbusmmap.filmApp.utils.BASE_API
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val filmsApi = Retrofit.Builder()
        .baseUrl(BASE_API)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmAPI::class.java)

    fun getTopFilms(
        callback: Callback<TopFilmsDataTransferObject>
    ) {
        filmsApi.getTopFilmsList().enqueue(callback)
    }
}