/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.addedit.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseFragment
import com.rssll971.fitnessassistantapp.featureexercise.di.ExercisesModule
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExercisesDeps
import dagger.Component

@[FeatureScope Component(
    dependencies = [FeatureExercisesDeps::class],
    modules = [ExercisesModule::class]
)]
internal interface AddEditComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureExercisesDeps: FeatureExercisesDeps): Builder
        fun build(): AddEditComponent
    }

    fun inject(fragment: AddEditExerciseFragment)
}