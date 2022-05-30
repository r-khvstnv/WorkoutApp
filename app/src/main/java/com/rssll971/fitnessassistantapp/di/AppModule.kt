package com.rssll971.fitnessassistantapp.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.MainViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.coredata.di.DatabaseModule
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelBuilderModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton


@Module(includes = [
    ViewModelBuilderModule::class,
    DatabaseModule::class
])
abstract class AppModule{
    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel

}