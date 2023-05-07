package com.example.roomdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.roomdb.dao.NoteDao
import com.example.roomdb.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDap(): NoteDao

    companion object {

        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabasec(context: Context): AppDatabase {
            val tempInstance = Instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                Instance = instance
                return instance
            }
        }
    }

}