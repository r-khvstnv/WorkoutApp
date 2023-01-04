/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.core.di.module.ViewModelFactoryModule
import com.rssll971.fitnessassistantapp.featurebmi.calculation.BmiCalculationViewModel
import com.rssll971.fitnessassistantapp.featurebmi.history.BmiHistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class BmiModule {
    @Binds
    @[IntoMap ViewModelKey(BmiCalculationViewModel::class)]
    abstract fun bindsBmiCalculationViewModel(bmiCalculationViewModel: BmiCalculationViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(BmiHistoryViewModel::class)]
    abstract fun bindsBmiHistoryViewModel(bmiHistoryViewModel: BmiHistoryViewModel): ViewModel
}