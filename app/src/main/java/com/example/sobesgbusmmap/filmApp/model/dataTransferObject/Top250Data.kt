package com.example.sobesgbusmmap.filmApp.model.dataTransferObject

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Top250Data(
    @SerializedName("id")
    val id: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("fullTitle")
    val fullTitle: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("crew")
    val crew: String,
    @SerializedName("imDbRating")
    val imdbRating: String,
    @SerializedName("imDbRatingCount")
    val imdbRatingCount: String
) : Parcelable