package com.example.sobesgbusmmap.filmApp.model

import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject
import java.io.IOException

interface IRepository{

}

interface ITopFilmsRepository {
    fun getTopMovieList(callback: retrofit2.Callback<TopFilmsDataTransferObject>)
}

interface ITopMoviesCallback {
    fun onResponse(topList: List<String>)
    fun onFailure(e: IOException)
}