package com.example.sobesgbusmmap.mapApp.view.markerList

import com.example.sobesgbusmmap.mapApp.model.room.MarkerData

interface ISaveMarkerChanges {
    fun saveChanges(markerData: MarkerData, position:Int)
}