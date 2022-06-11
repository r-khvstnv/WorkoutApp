/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di

import com.rssll971.fitnessassistantapp.core.di.annotation.FeatureScope
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.OptionFirstFragment
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.OptionSecondFragment
import dagger.Component

@[FeatureScope Component(dependencies = [OptionsFSDeps::class])]
interface OptionsFSComponent {
    @Component.Builder
    interface Builder{
        fun deps(optionsFSDeps: OptionsFSDeps): Builder
        fun build(): OptionsFSComponent
    }

    fun inject(optionFirstFragment: OptionFirstFragment)
    fun inject(optionSecondFragment: OptionSecondFragment)
}