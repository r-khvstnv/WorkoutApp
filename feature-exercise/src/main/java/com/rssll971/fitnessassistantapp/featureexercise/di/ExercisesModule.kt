/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelFactoryModule
import com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseViewModel
import com.rssll971.fitnessassistantapp.featureexercise.all.AllExercisesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class ExercisesModule {
    @Binds
    @[IntoMap ViewModelKey(AddEditExerciseViewModel::class)]
    abstract fun bindsAddEditExerciseViewModel(addEditExerciseViewModel: AddEditExerciseViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(AllExercisesViewModel::class)]
    abstract fun bindsAllExercisesViewModel(allExercisesViewModel: AllExercisesViewModel): ViewModel
}