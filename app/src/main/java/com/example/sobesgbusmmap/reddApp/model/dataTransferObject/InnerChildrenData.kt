package com.example.sobesgbusmmap.reddApp.model.dataTransferObject

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InnerChildrenData(
    @SerializedName("data")
    val innerData: InnerData
) : Parcelable
