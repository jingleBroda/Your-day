package com.example.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.presentationModel.TaskDay
import com.example.domain.presentationModel.TimeDay

@Entity(
    tableName = "TaskDayEntity",
)
data class TaskDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Long?, //для DiffUtil
    val day:String,
    val name:String,
    val hour:Int, //val time: TimeDay, //Time???
    val minute:Int,
    val sticker:Int, //Resourse
    val complete:Boolean
) {
    fun toTaskDay():TaskDay = TaskDay(
        id!!,
        day,
        name,
        TimeDay(hour, minute),
        sticker,
        complete
    )

    companion object{
        fun toTaskDayEntity(task:TaskDay) = with(task){
            TaskDayEntity(
                null,
                day,
                name,
                time.hour,
                time.minute,
                sticker,
                complete
            )
        }
    }
}