/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di.app

import com.rssll971.fitnessassistantapp.coredata.di.UseCaseModule
import dagger.Module

@Module(includes = [
    UseCaseModule::class
])
object AppModule