package com.rssll971.fitnessassistantapp.featureexercise.all.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureexercise.all.AllExercisesFragment
import com.rssll971.fitnessassistantapp.featureexercise.utils.AllExercisesDeps
import dagger.Component

@[FeatureScope Component(dependencies = [AllExercisesDeps::class])]
interface AllExercisesComponent {
    @Component.Builder
    interface Builder{
        fun deps(allExercisesDeps: AllExercisesDeps): Builder
        fun build(): AllExercisesComponent
    }

    fun inject(fragment: AllExercisesFragment)
}
