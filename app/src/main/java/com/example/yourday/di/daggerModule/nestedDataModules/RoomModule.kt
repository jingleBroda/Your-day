package com.example.yourday.di.daggerModule.nestedDataModules

import androidx.room.Room
import com.example.data.room.YourDayDao
import com.example.data.room.YourDayDataBase
import com.example.yourday.YourDayApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {
    @Singleton
    @Provides
    fun provideDataBase(app:YourDayApp):YourDayDataBase =
        Room.databaseBuilder(
            app,
            YourDayDataBase::class.java,
            "YourDayDataBase"
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun getDao(db:YourDayDataBase):YourDayDao = db.databaseDao()
}