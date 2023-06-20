package com.example.sobesgbusmmap.filmApp.utils

import android.graphics.Color
import com.example.sobesgbusmmap.filmApp.model.Movie
import com.example.sobesgbusmmap.filmApp.model.dataTransferObject.TopFilmsDataTransferObject

fun convertDTOToTopMovieList(topFilmsDataTransferObject: TopFilmsDataTransferObject): List<Movie> {
    val convertedList: MutableList<Movie> = mutableListOf()
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

fun convertRatingToColor(rating: String): Int {
    val convertedColor = if (rating.toDouble() > 5.0) {
        Color.argb(255, getRed(rating.toDouble()), 200, 0)
    } else {
        Color.argb(255, 200, getGreen(rating.toDouble()), 0)
    }
    return convertedColor
}

fun getGreen(toDouble: Double) = (toDouble / 5.0 * 200).toInt()

fun getRed(toDouble: Double) = ((5.0 - toDouble) / 5.0 * 200).toInt()
