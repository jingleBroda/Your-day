package com.example.data

import com.example.data.room.YourDayDao
import com.example.data.room.entities.TaskDayEntity
import com.example.domain.dataAbstract.DomainRepository
import com.example.domain.presentationModel.TaskDay
import javax.inject.Inject

class DataImplementationRepository @Inject constructor(
    private val dao:YourDayDao
):DomainRepository() {
    override suspend fun createTask(task: TaskDay) =
        dao.createTask(TaskDayEntity.toTaskDayEntity(task))

    override suspend fun getWeekTask(intervalDay: List<String>): Map<String, List<TaskDay>>{
        val result = mutableMapOf<String, List<TaskDay>>()
        run{
            intervalDay.forEach { day->
                val localResult = dao.getWeekTask(day).map { taskDb->
                    taskDb.toTaskDay()
                }
                if(localResult.isNotEmpty()) result[day] = localResult else result[day] = listOf()
            }
        }
        return result
    }

    override suspend fun deleteAllTask() = dao.deleteAllTask()
}