package com.example.sobesgbusmmap.model.repository

import com.example.sobesgbusmmap.model.room.MarkerData
import com.example.sobesgbusmmap.model.room.MarkersEntity

interface IMarkersRepository {
    suspend fun insertNewMarkerToData(markerData: MarkerData)

    suspend fun getAllMarkersData(): List<MarkerData>

    suspend fun removeMarkerFromData(id: Long)
}