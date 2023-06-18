package com.example.sobesgbusmmap.mapApp.app

import android.app.Application
import androidx.room.Room
import com.example.sobesgbusmmap.mapApp.model.room.IMarkersDAO
import com.example.sobesgbusmmap.mapApp.model.room.MarkersDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: MarkersDatabase? = null
        private const val DB_NAME = "markerdatabase.db"
        fun getMarkersDao(): IMarkersDAO {
            if (db == null) {
                synchronized(MarkersDatabase::class.java) {
                    if (db == null) {
                        if (appInstance == null) throw
                        IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            MarkersDatabase::class.java,
                            DB_NAME
                        )
                            .build()
                    }
                }
            }
            return db!!.getMarkersDAO()
        }
    }
}