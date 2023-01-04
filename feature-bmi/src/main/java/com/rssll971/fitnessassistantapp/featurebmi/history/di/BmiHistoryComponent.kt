/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.history.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featurebmi.di.BmiModule
import com.rssll971.fitnessassistantapp.featurebmi.di.FeatureBmiDeps
import com.rssll971.fitnessassistantapp.featurebmi.history.BmiHistoryFragment
import dagger.Component

@[FeatureScope Component(
    dependencies = [FeatureBmiDeps::class],
    modules = [BmiModule::class]
)]
internal interface BmiHistoryComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureBmiDeps: FeatureBmiDeps): Builder
        fun build(): BmiHistoryComponent
    }

    fun inject(bmiHistoryFragment: BmiHistoryFragment)
}