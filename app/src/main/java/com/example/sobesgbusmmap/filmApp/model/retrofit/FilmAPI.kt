package com.example.sobesgbusmmap.filmApp.model.retrofit

import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject
import com.example.sobesgbusmmap.filmApp.utils.API_KEY
import com.example.sobesgbusmmap.filmApp.utils.LANGUAGE_API
import com.example.sobesgbusmmap.filmApp.utils.TOP_250
import retrofit2.Call
import retrofit2.http.GET

interface FilmAPI {

    @GET(LANGUAGE_API + TOP_250 + API_KEY)
    fun getTopFilmsList(): Call<TopFilmsDataTransferObject>
}