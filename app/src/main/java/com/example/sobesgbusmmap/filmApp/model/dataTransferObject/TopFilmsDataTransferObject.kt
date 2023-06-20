package com.example.sobesgbusmmap.filmApp.model.dataTransferObject

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class TopFilmsDataTransferObject(
    @SerializedName("items")
    val listTop250Data: List<Top250Data>
):Parcelable
