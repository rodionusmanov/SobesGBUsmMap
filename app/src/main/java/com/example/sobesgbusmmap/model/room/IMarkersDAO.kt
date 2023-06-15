package com.example.sobesgbusmmap.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IMarkersDAO {

    @Insert(entity = MarkersEntity::class)
    suspend fun insertNewMarkerData(markerData: MarkersEntity)

    @Query("SELECT * FROM markers_table")
    suspend fun getAllMarkers(): List<MarkersEntity>

    @Query("DELETE FROM markers_table WHERE id = :markerId")
    suspend fun deleteMarkerFromDataById(markerId: Long)
}