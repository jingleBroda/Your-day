package com.example.yourday.di.daggerModule

import com.example.yourday.di.daggerModule.nestedDomainModules.UseCasesModule
import dagger.Module

@Module(
    includes = [
        UseCasesModule::class,
    ]
)
class DomainDaggerModule
