/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkout.workout.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelFactoryModule
import com.rssll971.fitnessassistantapp.featureworkout.workout.WorkoutViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class WorkoutModule {
    @Binds
    @[IntoMap ViewModelKey(WorkoutViewModel::class)]
    abstract fun bindsWorkoutViewModel(workoutViewModel: WorkoutViewModel): ViewModel
}