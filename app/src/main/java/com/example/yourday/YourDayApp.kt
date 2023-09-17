package com.example.yourday

import android.app.Activity
import com.example.yourday.di.DaggerYourDayDaggerComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


class YourDayApp:DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerYourDayDaggerComponent.builder().application(this).build()

}