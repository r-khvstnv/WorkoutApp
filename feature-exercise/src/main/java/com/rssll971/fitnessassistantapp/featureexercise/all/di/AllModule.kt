package com.rssll971.fitnessassistantapp.featureexercise.all.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.featureexercise.all.AllViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AllModule {
    @Binds
    @[IntoMap ViewModelKey(AllViewModel::class)]
    abstract fun providesAllViewModel(allViewModel: AllViewModel): ViewModel
}