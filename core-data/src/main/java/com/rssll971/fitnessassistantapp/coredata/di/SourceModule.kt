/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.di

import android.app.Application
import androidx.room.Room
import com.rssll971.fitnessassistantapp.coredata.data.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.BmiSource
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.ExerciseSource
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.StatisticSource
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class SourceModule {
    @[Singleton Provides]
    fun providesWorkoutDatabase(application: Application): WorkoutDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            WorkoutDatabase::class.java,
            Constants.APP_DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @[Singleton Provides]
    fun providesBmiSource(workoutDatabase: WorkoutDatabase) = BmiSource(workoutDatabase)

    @[Singleton Provides]
    fun providesExerciseSource(workoutDatabase: WorkoutDatabase) = ExerciseSource(workoutDatabase)

    @[Singleton Provides]
    fun providesStatisticSource(workoutDatabase: WorkoutDatabase) = StatisticSource(workoutDatabase)
}