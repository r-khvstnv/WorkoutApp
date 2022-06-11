/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.calculation.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featurebmi.calculation.BmiCalculationFragment
import com.rssll971.fitnessassistantapp.featurebmi.utils.FeatureBmiDeps
import dagger.Component

@[FeatureScope Component(dependencies = [FeatureBmiDeps::class])]
interface BmiCalculationComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureBmiDeps: FeatureBmiDeps): Builder
        fun build(): BmiCalculationComponent
    }

    fun inject(bmiCalculationFragment: BmiCalculationFragment)
}