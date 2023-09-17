package com.example.yourday.di.daggerModule.nestedPresentationModules

import com.example.presentation.main.fragment.mainFragment.MainMenuFragment
import com.example.presentation.main.fragment.mainFragment.createTaskDialog.CreateTaskDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun providesMainFragment():MainMenuFragment

    @ContributesAndroidInjector
    abstract fun providesCreateTaskDialogFragment():CreateTaskDialogFragment
}