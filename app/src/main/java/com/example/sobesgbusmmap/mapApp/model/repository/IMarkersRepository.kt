package com.example.sobesgbusmmap.mapApp.model.repository

import com.example.sobesgbusmmap.mapApp.model.room.MarkerData

interface IMarkersRepository {
    suspend fun insertNewMarkerToData(markerData: MarkerData)

    suspend fun getAllMarkersData(): List<MarkerData>

    suspend fun removeMarkerFromData(id: Long)
}