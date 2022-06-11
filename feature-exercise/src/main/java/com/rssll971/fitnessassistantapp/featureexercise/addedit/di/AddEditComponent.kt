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
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExercisesDeps
import dagger.Component

@[FeatureScope Component(
    dependencies = [FeatureExercisesDeps::class]
)]
interface AddEditComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureExercisesDeps: FeatureExercisesDeps): Builder
        fun build(): AddEditComponent
    }

    fun inject(fragment: AddEditExerciseFragment)
}