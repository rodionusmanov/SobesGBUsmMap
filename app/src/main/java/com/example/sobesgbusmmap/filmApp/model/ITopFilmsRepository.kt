package com.example.sobesgbusmmap.filmApp.model

import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject

interface ITopFilmsRepository {
    fun getTopMovieList(callback: retrofit2.Callback<TopFilmsDataTransferObject>)
}