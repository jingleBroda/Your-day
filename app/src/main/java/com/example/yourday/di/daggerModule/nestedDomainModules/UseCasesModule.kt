package com.example.yourday.di.daggerModule.nestedDomainModules

import com.example.domain.dataAbstract.DomainRepository
import com.example.domain.dataAbstract.useCase.CreateTaskUseCase
import com.example.domain.dataAbstract.useCase.DeleteAllTaskUseCase
import com.example.domain.dataAbstract.useCase.GetDayTaskUseCase
import com.example.domain.dataAbstract.useCase.GetWeekTaskUseCase
import com.example.domain.dataAbstract.useCase.UpdateCompleteStatusTaskUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {
    @Provides
    fun provideCreateTaskUseCase(repository:DomainRepository):CreateTaskUseCase =
        CreateTaskUseCase(repository)

    @Provides
    fun provideGetWeekTaskUseCase(repository:DomainRepository): GetWeekTaskUseCase =
        GetWeekTaskUseCase(repository)

    @Provides
    fun provideDeleteAllTaskUseCase(repository:DomainRepository): DeleteAllTaskUseCase =
        DeleteAllTaskUseCase(repository)

    @Provides
    fun provideGetDayTaskUseCase(repository:DomainRepository): GetDayTaskUseCase =
        GetDayTaskUseCase(repository)

    @Provides
    fun provideUpdateCompleteStatusTaskUseCase(repository:DomainRepository): UpdateCompleteStatusTaskUseCase =
        UpdateCompleteStatusTaskUseCase(repository)
}