package com.example.sobesgbusmmap.mapApp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [MarkersEntity::class]
)
abstract class MarkersDatabase : RoomDatabase() {
    abstract fun getMarkersDAO(): IMarkersDAO
}