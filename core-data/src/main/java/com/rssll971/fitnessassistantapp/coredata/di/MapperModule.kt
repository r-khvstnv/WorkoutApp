/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.di

import com.rssll971.fitnessassistantapp.coredata.data.mapper.*
import com.rssll971.fitnessassistantapp.coredata.data.mapper.BmiEntityToParamMapper
import com.rssll971.fitnessassistantapp.coredata.data.mapper.BmiParamToEntityMapper
import com.rssll971.fitnessassistantapp.coredata.data.mapper.ExerciseEntityToParamMapper
import com.rssll971.fitnessassistantapp.coredata.data.mapper.ExerciseParamToEntityMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class MapperModule {
    @[Singleton Provides]
    fun providesBmiEntityToParamMapper() = BmiEntityToParamMapper()
    @[Singleton Provides]
    fun providesBmiParamToEntityMapper() = BmiParamToEntityMapper()
    @[Singleton Provides]
    fun providesExerciseEntityToParamMapper() = ExerciseEntityToParamMapper()
    @[Singleton Provides]
    fun providesExerciseParamToEntityMapper() = ExerciseParamToEntityMapper()
    @[Singleton Provides]
    fun providesStatisticEntityToParamMapper() = StatisticEntityToParamMapper()
    @[Singleton Provides]
    fun providesStatisticParamToEntityMapper() = StatisticParamToEntityMapper()
}