package com.rssll971.fitnessassistantapp.featurebmi.history.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featurebmi.utils.FeatureBmiDeps
import com.rssll971.fitnessassistantapp.featurebmi.history.BmiHistoryFragment
import dagger.Component

@[FeatureScope Component(dependencies = [FeatureBmiDeps::class])]
interface BmiHistoryComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureBmiDeps: FeatureBmiDeps): Builder
        fun build(): BmiHistoryComponent
    }

    fun inject(bmiHistoryFragment: BmiHistoryFragment)
}