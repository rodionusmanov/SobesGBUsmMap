package com.example.sobesgbusmmap.model.repository

import com.example.sobesgbusmmap.model.room.IMarkersDAO
import com.example.sobesgbusmmap.model.room.MarkerData
import com.example.sobesgbusmmap.utils.convertMarkerToEntity
import com.example.sobesgbusmmap.utils.convertMarkersEntityToList

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