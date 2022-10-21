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
import com.rssll971.fitnessassistantapp.coredata.data.repository.BmiRepositoryImpl
import com.rssll971.fitnessassistantapp.coredata.data.repository.ExerciseRepositoryImpl
import com.rssll971.fitnessassistantapp.coredata.data.repository.StatisticRepositoryImpl
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.BmiSource
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.ExerciseSource
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.StatisticSource
import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [SourceModule::class, MapperModule::class])
internal class RepositoryModule {
    @[Singleton Provides]
    fun providesBmiRepository(
        source: BmiSource,
        bmiEntityToParamMapper: BmiEntityToParamMapper,
        bmiParamToEntityMapper: BmiParamToEntityMapper
    ): BmiRepository = BmiRepositoryImpl(source, bmiEntityToParamMapper, bmiParamToEntityMapper)

    @[Singleton Provides]
    fun providesExerciseRepository(
        source: ExerciseSource,
        exerciseEntityToParamMapper: ExerciseEntityToParamMapper,
        exerciseParamToEntityMapper: ExerciseParamToEntityMapper
    ): ExerciseRepository = ExerciseRepositoryImpl(source, exerciseEntityToParamMapper, exerciseParamToEntityMapper)

    @[Singleton Provides]
    fun providesStatisticRepository(
        source: StatisticSource,
        statisticEntityToParamMapper: StatisticEntityToParamMapper,
        statisticParamToEntityMapper: StatisticParamToEntityMapper
    ): StatisticRepository = StatisticRepositoryImpl(source, statisticEntityToParamMapper, statisticParamToEntityMapper)
}