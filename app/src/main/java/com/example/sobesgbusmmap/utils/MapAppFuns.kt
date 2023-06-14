package com.example.sobesgbusmmap.utils

import com.example.sobesgbusmmap.model.room.MarkerData
import com.example.sobesgbusmmap.model.room.MarkersEntity

fun convertMarkersEntityToList(entityList: List<MarkersEntity>): List<MarkerData> {
    return entityList.map {
        MarkerData(it.id, it.markerName, it.markerLat, it.markerLng, it.markerAnnotation)
    }
}

fun convertMarkerToEntity(marker: MarkerData): MarkersEntity {
    return MarkersEntity(
        marker.markerId,
        marker.markerName,
        marker.markerCoordinatesLat,
        marker.markerCoordinatesLng,
        marker.markerAnnotation
    )
}
