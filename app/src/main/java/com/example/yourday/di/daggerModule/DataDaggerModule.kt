package com.example.yourday.di.daggerModule

import com.example.yourday.di.daggerModule.nestedDataModules.RepositoryModule
import com.example.yourday.di.daggerModule.nestedDataModules.RoomModule
import dagger.Module

@Module(
    includes = [
        RepositoryModule::class,
        RoomModule::class,
    ]
)
class DataDaggerModule
