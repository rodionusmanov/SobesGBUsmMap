package com.example.sobesgbusmmap.reddApp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sobesgbusmmap.reddApp.model.RedditRepository
import com.example.sobesgbusmmap.reddApp.model.dataTransferObject.InnerChildrenData

class RedditViewModel (
    private val redditRepository: RedditRepository
    ): ViewModel() {

    val errorMessage = MutableLiveData<String>()

    fun getRedditList(): LiveData<PagingData<InnerChildrenData>>{
        return redditRepository.getAllData().cachedIn(viewModelScope)
    }
}