/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.OptionStartFragment
import dagger.Component

@[FeatureScope Component(
    dependencies = [OptionStartDeps::class],
    modules = [OptionStartModule::class]
)]
internal interface OptionStartComponent {
    @Component.Builder
    interface Builder{
        fun deps(optionStartDeps: OptionStartDeps): Builder
        fun build(): OptionStartComponent
    }

    fun inject(optionStartFragment: OptionStartFragment)
}
