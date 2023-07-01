package com.example.sobesgbusmmap.reddApp.model.retrofit

import com.example.sobesgbusmmap.filmApp.utils.REDDIT_GET
import com.example.sobesgbusmmap.filmApp.utils.REDDIT_HOT_BASE_URL
import com.example.sobesgbusmmap.reddApp.model.dataTransferObject.RedditDTO
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditRetrofitService {

    @GET(REDDIT_GET)
    suspend fun getRedditHotList(
        @Query("limit") limit:Int,
        @Query("after") after:String,
        @Query("count") count:Int
    ): Response<RedditDTO>

    companion object {
        private var retrofitService: RedditRetrofitService? = null
        fun getInstance(): RedditRetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(REDDIT_HOT_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RedditRetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}