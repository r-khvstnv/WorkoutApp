/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.all.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureexercise.all.AllExercisesFragment
import com.rssll971.fitnessassistantapp.featureexercise.di.ExercisesModule
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExercisesDeps
import dagger.Component

@[FeatureScope Component(
    dependencies = [FeatureExercisesDeps::class],
    modules = [ExercisesModule::class]
)]
internal interface AllExercisesComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureExercisesDeps: FeatureExercisesDeps): Builder
        fun build(): AllExercisesComponent
    }

    fun inject(fragment: AllExercisesFragment)
}
