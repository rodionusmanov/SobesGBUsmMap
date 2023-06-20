package com.example.sobesgbusmmap.filmApp.viewModel

import com.example.sobesgbusmmap.filmApp.model.Movie

sealed class FilmsFragmentAppState {
    data class Success(val movieList: List<Movie>) : FilmsFragmentAppState()
    data class Error(val error: Any) : FilmsFragmentAppState()
    object Loading : FilmsFragmentAppState()
}
