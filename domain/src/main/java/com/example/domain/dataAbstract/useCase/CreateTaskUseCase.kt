package com.example.domain.dataAbstract.useCase

import com.example.domain.dataAbstract.DomainRepository
import com.example.domain.presentationModel.TaskDay

class CreateTaskUseCase(private val repository:DomainRepository) {
    suspend operator fun invoke(task: TaskDay) = repository.createTask(task)
}