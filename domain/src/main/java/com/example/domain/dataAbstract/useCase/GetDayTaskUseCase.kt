package com.example.domain.dataAbstract.useCase

import com.example.domain.dataAbstract.DomainRepository
import com.example.domain.presentationModel.TaskDay

class GetDayTaskUseCase(private val repository: DomainRepository) {
    suspend operator fun invoke(day:String):List<TaskDay> = repository.getDayTask(day)
}