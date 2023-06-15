package com.example.sobesgbusmmap.view.markerList

import com.example.sobesgbusmmap.model.room.MarkerData

interface ISaveMarkerChanges {
    fun saveChanges(markerData: MarkerData, position:Int)
}