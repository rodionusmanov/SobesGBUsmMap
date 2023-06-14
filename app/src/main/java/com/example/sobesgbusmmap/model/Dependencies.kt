package com.example.sobesgbusmmap.model

import android.content.Context
import androidx.room.Room
import com.example.sobesgbusmmap.model.repository.MarkersRepositoryImpl
import com.example.sobesgbusmmap.model.room.MarkersDatabase

object Dependencies {

    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: MarkersDatabase by lazy {
        Room.databaseBuilder(applicationContext, MarkersDatabase::class.java, "markerdatabase.db")
            .build()
    }

    val markersRepository: MarkersRepositoryImpl by lazy { MarkersRepositoryImpl(appDatabase.getMarkersDAO()) }
}