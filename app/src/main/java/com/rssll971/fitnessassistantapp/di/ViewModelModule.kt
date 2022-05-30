package com.rssll971.fitnessassistantapp.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.featureexercise.all.AllExercisesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(AllExercisesViewModel::class)]
    abstract fun providesAllViewModel(allExercisesViewModel: AllExercisesViewModel): ViewModel
}