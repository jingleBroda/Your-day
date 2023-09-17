package com.example.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.room.entities.TaskDayEntity
import com.example.domain.presentationModel.TaskDay

@Dao
interface YourDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createTask(task: TaskDayEntity):Long

    @Query("SELECT * FROM TaskDayEntity WHERE day=:requireDay ")
    suspend fun getWeekTask(requireDay:String):List<TaskDayEntity>

    @Query("DELETE FROM TaskDayEntity")
    suspend fun deleteAllTask()
}