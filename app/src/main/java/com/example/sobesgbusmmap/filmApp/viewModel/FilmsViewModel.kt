package com.example.sobesgbusmmap.filmApp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sobesgbusmmap.filmApp.model.RemoteDataSource
import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject
import com.example.sobesgbusmmap.filmApp.model.retrofit.RepositoryFilmRetrofitImpl
import com.example.sobesgbusmmap.filmApp.utils.convertDTOToTopMovieList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"


class FilmsViewModel(
    val filmsLiveData: MutableLiveData<FilmsFragmentAppState> = MutableLiveData(),
    private val repositoryFilmRetrofitImpl: RepositoryFilmRetrofitImpl = RepositoryFilmRetrofitImpl(
        RemoteDataSource()
    )
) :
    ViewModel() {

    fun getTopMoviesList() {
        filmsLiveData.value = FilmsFragmentAppState.Loading
        repositoryFilmRetrofitImpl.getTopMovieList(callback)
    }

    private val callback = object : Callback<TopFilmsDataTransferObject> {
        override fun onResponse(
            call: Call<TopFilmsDataTransferObject>,
            response: Response<TopFilmsDataTransferObject>
        ) {
            val serverResponse: TopFilmsDataTransferObject? = response.body()
            filmsLiveData.postValue(
                (if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    filmsLiveData.value = FilmsFragmentAppState.Error(Throwable(SERVER_ERROR))
                    FilmsFragmentAppState.Error(Throwable(SERVER_ERROR))
                })
            )
        }

        override fun onFailure(call: Call<TopFilmsDataTransferObject>, t: Throwable) {
            filmsLiveData.value = FilmsFragmentAppState.Error(Throwable(t.message ?: REQUEST_ERROR))
            filmsLiveData.postValue(FilmsFragmentAppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: TopFilmsDataTransferObject): FilmsFragmentAppState {
        val movieList = serverResponse.listTop250Data
        return if (movieList == null) {
            filmsLiveData.value = FilmsFragmentAppState.Error(Throwable(CORRUPTED_DATA))
            FilmsFragmentAppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            filmsLiveData.value = FilmsFragmentAppState.Success(convertDTOToTopMovieList(serverResponse))
            FilmsFragmentAppState.Success(convertDTOToTopMovieList(serverResponse))
        }
    }
}