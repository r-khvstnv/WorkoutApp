package com.rssll971.fitnessassistantapp.core.di.module

import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.core.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelBuilderModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}