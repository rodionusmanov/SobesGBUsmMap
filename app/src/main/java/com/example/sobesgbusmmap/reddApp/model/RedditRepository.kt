package com.example.sobesgbusmmap.reddApp.model

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.sobesgbusmmap.reddApp.model.dataTransferObject.InnerChildrenData
import com.example.sobesgbusmmap.reddApp.model.retrofit.RedditRetrofitService

class RedditRepository constructor(
    private val retrofitService: RedditRetrofitService
    ) {

    fun getAllData(): LiveData<PagingData<InnerChildrenData>>{
        return Pager(
            config = PagingConfig(
                pageSize = 30,
                enablePlaceholders = false,
                initialLoadSize = 3,
                prefetchDistance = 3
            ),
            pagingSourceFactory = {
                RedditPagingSource(retrofitService)
            }
        , initialKey = 1
        ).liveData
    }
}