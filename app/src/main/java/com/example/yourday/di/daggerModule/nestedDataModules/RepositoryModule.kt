package com.example.yourday.di.daggerModule.nestedDataModules

import com.example.data.DataImplementationRepository
import com.example.data.room.YourDayDao
import com.example.domain.dataAbstract.DomainRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(dao:YourDayDao):DomainRepository = DataImplementationRepository(dao)
}