/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di

import com.rssll971.fitnessassistantapp.core.di.module.ViewModelBuilderModule
import com.rssll971.fitnessassistantapp.coredata.di.DatabaseModule
import dagger.Module

@Module(includes = [
    ViewModelBuilderModule::class,
    ViewModelModule::class,
    DatabaseModule::class
])
object AppModule