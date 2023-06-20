package com.example.sobesgbusmmap.filmApp.model.retrofit

import com.example.sobesgbusmmap.filmApp.model.ITopFilmsRepository
import com.example.sobesgbusmmap.filmApp.model.RemoteDataSource
import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject
import retrofit2.Callback
import retrofit2.Retrofit

class RepositoryFilmRetrofitImpl(private val remoteDataSource: RemoteDataSource) : ITopFilmsRepository {

    override fun getTopMovieList(callback: Callback<TopFilmsDataTransferObject>) {
        remoteDataSource.getTopFilms(callback)
    }

}