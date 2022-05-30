package com.rssll971.fitnessassistantapp.di

import com.rssll971.fitnessassistantapp.coredata.di.DatabaseModule
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelBuilderModule
import dagger.Module

@Module(includes = [
    ViewModelBuilderModule::class,
    ViewModelModule::class,
    DatabaseModule::class
])
object AppModule