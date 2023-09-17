package com.example.yourday.di.daggerModule

import com.example.yourday.di.daggerModule.nestedPresentationModules.FragmentModule
import com.example.yourday.di.daggerModule.nestedPresentationModules.ViewModelModule
import dagger.Module

@Module(
    includes = [
        FragmentModule::class,
        ViewModelModule::class,
    ]
)
class PresentationDaggerModule
