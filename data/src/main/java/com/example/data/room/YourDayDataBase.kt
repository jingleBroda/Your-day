package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.room.entities.TaskDayEntity

@Database(
    entities = [
        TaskDayEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class YourDayDataBase:RoomDatabase() {
    abstract fun databaseDao():YourDayDao
}