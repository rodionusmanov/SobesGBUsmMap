package com.example.sobesgbusmmap.mapApp.model.repository

import com.example.sobesgbusmmap.mapApp.model.room.IMarkersDAO
import com.example.sobesgbusmmap.mapApp.model.room.MarkerData
import com.example.sobesgbusmmap.mapApp.utils.convertMarkerToEntity
import com.example.sobesgbusmmap.mapApp.utils.convertMarkersEntityToList

class MarkersRepositoryImpl(private val markersDAO: IMarkersDAO): IMarkersRepository {

    override suspend fun insertNewMarkerToData(markerData: MarkerData) {
        markersDAO.insertNewMarkerData(convertMarkerToEntity(markerData))
    }

    override suspend fun getAllMarkersData(): List<MarkerData> {
        return convertMarkersEntityToList(markersDAO.getAllMarkers())
    }

    override suspend fun removeMarkerFromData(id: Long) {
        markersDAO.deleteMarkerFromDataById(id)
    }
}