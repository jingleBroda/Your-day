package com.example.presentation.main.fragment.mainFragment.createTaskDialog

import androidx.lifecycle.ViewModel
import com.example.domain.dataAbstract.useCase.CreateTaskUseCase
import com.example.domain.dataAbstract.useCase.GetDayTaskUseCase
import com.example.domain.presentationModel.TaskDay
import com.example.domain.presentationModel.TimeDay
import javax.inject.Inject

class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase:CreateTaskUseCase,
    private val getTaskReadDayUseCase: GetDayTaskUseCase
): ViewModel() {
    private var _dayTask = listOf<TaskDay>()
    val dayTask:List<TaskDay>
        get() = _dayTask
    suspend fun createTask(task: TaskDay) = createTaskUseCase.invoke(task)
    suspend fun getTaskReadDayUseCase(day:String){
        _dayTask = getTaskReadDayUseCase.invoke(day)
    }

    fun isDuplicateTask(timeDay: TimeDay):Boolean{
        var result = false
        for(day in _dayTask){
            if((day.time.hour == timeDay.hour)&&(day.time.minute == timeDay.minute)){
                result = true
                break
            }
        }
        return result
    }
}