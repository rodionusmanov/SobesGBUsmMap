package com.example.sobesgbusmmap.model.room

data class MarkerData(
    val markerId: Long,
    var markerName: String,
    var markerCoordinatesLat: Double,
    var markerCoordinatesLng: Double,
    var markerAnnotation: String
    )
