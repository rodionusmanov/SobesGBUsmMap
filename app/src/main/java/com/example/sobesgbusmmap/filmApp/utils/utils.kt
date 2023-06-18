package com.example.sobesgbusmmap.filmApp.utils

import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject

fun convertDTOToTopMovieList(topFilmsDataTransferObject: TopFilmsDataTransferObject): List<Movie> {
    val convertedList: MutableList<Movie> = mutableListOf<Movie>()
    topFilmsDataTransferObject.listTop250Data.forEach {
        convertedList.add(
            Movie(
                it.id,
                it.rank,
                it.title,
                it.fullTitle,
                it.year,
                it.image,
                it.crew,
                it.imdbRating,
                it.imdbRatingCount
            )
        )
    }

    return convertedList
}