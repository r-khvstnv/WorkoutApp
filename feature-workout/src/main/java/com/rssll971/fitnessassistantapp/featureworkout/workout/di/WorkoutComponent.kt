package com.rssll971.fitnessassistantapp.featureworkout.workout.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureworkout.workout.WorkoutFragment
import dagger.Component

@[FeatureScope Component(dependencies = [WorkoutDeps::class])]
interface WorkoutComponent {
    @Component.Builder
    interface Builder{
        fun deps(workoutDeps: WorkoutDeps): Builder
        fun build(): WorkoutComponent
    }

    fun inject(workoutFragment: WorkoutFragment)
}