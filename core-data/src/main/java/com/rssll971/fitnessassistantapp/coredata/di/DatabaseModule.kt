package com.rssll971.fitnessassistantapp.coredata.di

import android.app.Application
import androidx.room.Room
import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesWorkoutDatabase(application: Application): WorkoutDatabase{
        return Room.databaseBuilder(
            application.applicationContext,
            WorkoutDatabase::class.java,
            Constants.APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesBmiRepository(database: WorkoutDatabase) = BmiRepository(database)
    @Singleton
    @Provides
    fun providesExerciseRepository(database: WorkoutDatabase) = ExerciseRepository(database)
    @Singleton
    @Provides
    fun providesStatisticRepository(database: WorkoutDatabase) = StatisticRepository(database)
}