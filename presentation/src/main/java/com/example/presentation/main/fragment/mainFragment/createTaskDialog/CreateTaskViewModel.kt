package com.example.presentation.main.fragment.mainFragment.createTaskDialog

import androidx.lifecycle.ViewModel
import com.example.domain.dataAbstract.useCase.CreateTaskUseCase
import com.example.domain.presentationModel.TaskDay
import javax.inject.Inject

class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase:CreateTaskUseCase
): ViewModel() {

    suspend fun createTask(task: TaskDay) = createTaskUseCase.invoke(task)
}