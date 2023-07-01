package com.example.sobesgbusmmap.reddApp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sobesgbusmmap.reddApp.model.RedditRepository

class RedditViewModelFactory constructor(private val redditRepository: RedditRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RedditViewModel::class.java)){
            RedditViewModel(this.redditRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}