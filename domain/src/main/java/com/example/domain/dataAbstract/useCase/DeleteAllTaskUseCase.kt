package com.example.domain.dataAbstract.useCase

import com.example.domain.dataAbstract.DomainRepository

class DeleteAllTaskUseCase(private val repository: DomainRepository) {
    suspend operator fun invoke() = repository.deleteAllTask()
}