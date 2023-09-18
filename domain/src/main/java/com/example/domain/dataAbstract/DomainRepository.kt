package com.example.domain.dataAbstract

import com.example.domain.presentationModel.TaskDay

abstract class DomainRepository {
    abstract suspend fun createTask(task:TaskDay):Long
    abstract suspend fun getWeekTask(intervalDay:List<String>):Map<String, List<TaskDay>>
    abstract suspend fun deleteAllTask()
    abstract suspend fun getDayTask(day:String):List<TaskDay>
}