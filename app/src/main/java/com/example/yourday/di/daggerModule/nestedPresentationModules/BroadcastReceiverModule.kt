package com.example.yourday.di.daggerModule.nestedPresentationModules

import com.example.presentation.main.receiver.CheckTomorrowTaskNotifyReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastReceiverModule {
    @ContributesAndroidInjector
    abstract fun contributesMyTestReceiver() : CheckTomorrowTaskNotifyReceiver
}