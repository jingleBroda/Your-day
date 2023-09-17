package com.example.yourday.di

import com.example.yourday.YourDayApp
import com.example.yourday.di.daggerModule.DataDaggerModule
import com.example.yourday.di.daggerModule.DomainDaggerModule
import com.example.yourday.di.daggerModule.PresentationDaggerModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        PresentationDaggerModule::class,
        DataDaggerModule::class,
        DomainDaggerModule::class,
    ]
)
@Singleton
interface YourDayDaggerComponent: AndroidInjector<YourDayApp> {
    override fun inject(app: YourDayApp)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app:YourDayApp):Builder
        fun build():YourDayDaggerComponent
    }
}

