package com.example.sobesgbusmmap.reddApp.model

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sobesgbusmmap.filmApp.utils.lastAfter
import com.example.sobesgbusmmap.reddApp.model.dataTransferObject.InnerChildrenData
import com.example.sobesgbusmmap.reddApp.model.retrofit.RedditRetrofitService

class RedditPagingSource(private val apiService: RedditRetrofitService) :
    PagingSource<Int, InnerChildrenData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InnerChildrenData> {
        return try {
            val position = params.key ?: 1
            val response = apiService.getRedditHotList(30, lastAfter,10)
            LoadResult.Page(
                response.body()!!.data.childrenData,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, InnerChildrenData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}