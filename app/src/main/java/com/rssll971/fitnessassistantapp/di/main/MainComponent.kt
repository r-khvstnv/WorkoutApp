/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di.main

import com.rssll971.fitnessassistantapp.core.di.annotation.ActivityScope
import com.rssll971.fitnessassistantapp.di.app.AppComponent
import com.rssll971.fitnessassistantapp.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MainModule::class]
)
internal interface MainComponent {
    fun inject(mainActivity: MainActivity)
}