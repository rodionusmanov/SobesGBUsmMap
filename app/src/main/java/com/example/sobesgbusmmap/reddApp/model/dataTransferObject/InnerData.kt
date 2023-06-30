package com.example.sobesgbusmmap.reddApp.model.dataTransferObject

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InnerData(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("selftext")
    val selfText: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("num_comments")
    val numberOfComments: Int,
    @SerializedName("name")
    val name: String
):Parcelable
