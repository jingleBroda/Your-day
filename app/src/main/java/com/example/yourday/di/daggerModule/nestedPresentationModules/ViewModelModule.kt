package com.example.yourday.di.daggerModule.nestedPresentationModules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.main.fragment.mainFragment.MainMenuViewModel
import com.example.presentation.main.fragment.mainFragment.createTaskDialog.CreateTaskViewModel
import com.example.presentation.main.globalUtils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainMenuViewModel::class)
    internal abstract fun bindMainFragmentViewModel(
        mainMenuViewModel: MainMenuViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateTaskViewModel::class)
    internal abstract fun bindCreateTaskViewModel(
        createTaskViewModel: CreateTaskViewModel
    ): ViewModel
}