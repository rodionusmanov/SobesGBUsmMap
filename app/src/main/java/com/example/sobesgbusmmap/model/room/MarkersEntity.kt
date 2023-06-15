package com.example.sobesgbusmmap.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "markers_table", indices = [Index("id")])
data class MarkersEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "marker_name")
    var markerName: String,
    @ColumnInfo(name = "marker_coordinates")
    var markerLat: Double,
    var markerLng: Double,
    var markerAnnotation: String
)