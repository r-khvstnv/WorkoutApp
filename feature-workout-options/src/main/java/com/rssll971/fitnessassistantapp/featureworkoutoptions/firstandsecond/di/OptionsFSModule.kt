/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelFactoryModule
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.OptionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class OptionsFSModule {
    @Binds
    @[IntoMap ViewModelKey(OptionsViewModel::class)]
    abstract fun bindsOptionsViewModel(optionsViewModel: OptionsViewModel): ViewModel
}