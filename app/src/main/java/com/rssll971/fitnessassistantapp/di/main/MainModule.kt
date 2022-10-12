/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di.main

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelFactoryModule
import com.rssll971.fitnessassistantapp.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module(includes = [ViewModelFactoryModule::class])
internal abstract class MainModule {
    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel
}