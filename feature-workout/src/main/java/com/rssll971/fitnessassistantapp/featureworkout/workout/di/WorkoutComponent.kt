/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkout.workout.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureworkout.workout.WorkoutFragment
import dagger.Component

@[FeatureScope Component(
    dependencies = [WorkoutDeps::class],
    modules = [WorkoutModule::class]
)]
internal interface WorkoutComponent {
    @Component.Builder
    interface Builder{
        fun deps(workoutDeps: WorkoutDeps): Builder
        fun build(): WorkoutComponent
    }

    fun inject(workoutFragment: WorkoutFragment)
}