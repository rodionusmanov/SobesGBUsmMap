package com.example.sobesgbusmmap.reddApp.model.dataTransferObject

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChildrenRedditData(
    @SerializedName("children")
    val childrenData: List<InnerChildrenData>
) : Parcelable
