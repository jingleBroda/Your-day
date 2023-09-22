package com.example.domain.dataAbstract.useCase

import com.example.domain.dataAbstract.DomainRepository

class UpdateCompleteStatusTaskUseCase(private val repository: DomainRepository) {
    suspend operator fun invoke(day:String, name:String, completeStatus:Boolean) =
        repository.updateCompleteStatusTask(day, name, completeStatus)
}