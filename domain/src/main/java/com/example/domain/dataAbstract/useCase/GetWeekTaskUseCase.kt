package com.example.domain.dataAbstract.useCase

import com.example.domain.dataAbstract.DomainRepository
import com.example.domain.presentationModel.TaskDay

class GetWeekTaskUseCase(private val repository:DomainRepository) {
    suspend operator fun invoke(intervalDay:List<String>):Map<String, List<TaskDay>> =
        repository.getWeekTask(intervalDay)
}