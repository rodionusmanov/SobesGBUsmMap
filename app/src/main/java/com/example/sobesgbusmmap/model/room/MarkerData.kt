package com.example.sobesgbusmmap.model.room

import androidx.room.ColumnInfo
import com.google.android.gms.maps.model.LatLng

data class MarkerData(
    val markerId: Long,
    var markerName: String,
    var markerCoordinatesLat: Double,
    var markerCoordinatesLng: Double,
    var markerAnnotation: String
    )
