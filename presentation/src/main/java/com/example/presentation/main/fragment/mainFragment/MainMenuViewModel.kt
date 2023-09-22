package com.example.presentation.main.fragment.mainFragment

import androidx.lifecycle.ViewModel
import com.example.domain.dataAbstract.useCase.DeleteAllTaskUseCase
import com.example.domain.dataAbstract.useCase.GetWeekTaskUseCase
import com.example.domain.dataAbstract.useCase.UpdateCompleteStatusTaskUseCase
import com.example.domain.presentationModel.TaskDay
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(
    private val getWeekTaskUseCase:GetWeekTaskUseCase,
    private val deleteAllTaskUseCase:DeleteAllTaskUseCase,
    private val updateCompleteStatusTaskUseCase: UpdateCompleteStatusTaskUseCase
): ViewModel() {

    private var _weekTask = mutableMapOf<String, List<TaskDay>>()
    val weekTask:Map<String, List<TaskDay>>
        get() = _weekTask

    suspend fun getWeekTask(intervalDay:List<String>){
        _weekTask.clear()
        intervalDay.forEach { day->
            _weekTask[day] = listOf()
        }
        getWeekTaskUseCase.invoke(intervalDay).forEach { (day, listTask) ->
            _weekTask[day] = listTask
        }
    }

    suspend fun updateCompleteStatusTask(
        day:String,
        name:String,
        completeStatus:Boolean
    ) = updateCompleteStatusTaskUseCase.invoke(day, name, completeStatus)

    suspend fun deleteAllTask(){
        _weekTask.clear()
        deleteAllTaskUseCase.invoke()
    }

    fun addTaskInCreateDialog(newTask:TaskDay){
        val updateValue = _weekTask[newTask.day]?.toMutableList() ?: mutableListOf()
        updateValue.add(newTask)
        _weekTask[newTask.day] = updateValue
    }
}