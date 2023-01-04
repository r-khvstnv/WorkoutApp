/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.di

import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.AddBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.DeleteAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.GetAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.*
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetAllStatisticUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetLastStatisticUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
class UseCaseModule {
    @[Singleton Provides]
    fun providesAddBmiUseCase(repository: BmiRepository) = AddBmiUseCase(repository)
    @[Singleton Provides]
    fun providesDeleteAllBmiUseCase(repository: BmiRepository) = DeleteAllBmiUseCase(repository)
    @[Singleton Provides]
    fun providesGetAllBmiUseCase(repository: BmiRepository) = GetAllBmiUseCase(repository)

    @[Singleton Provides]
    fun providesAddExerciseUseCase(repository: ExerciseRepository) = AddExerciseUseCase(repository)
    @[Singleton Provides]
    fun providesDeleteExerciseUseCase(repository: ExerciseRepository) = DeleteExerciseUseCase(repository)
    @[Singleton Provides]
    fun providesGetAllExercisesUseCase(repository: ExerciseRepository) = GetAllExercisesUseCase(repository)
    @[Singleton Provides]
    fun providesGetExerciseByIdUseCase(repository: ExerciseRepository) = GetExerciseByIdUseCase(repository)
    @[Singleton Provides]
    fun providesUpdateExerciseUseCase(repository: ExerciseRepository) = UpdateExerciseUseCase(repository)
    @[Singleton Provides]
    fun providesGetExercisesByIdListUseCase(repository: ExerciseRepository) = GetExercisesByIdListUseCase(repository)

    @[Singleton Provides]
    fun providesAddStatisticUseCase(repository: StatisticRepository) = AddStatisticUseCase(repository)
    @[Singleton Provides]
    fun providesGetAllStatisticUseCase(repository: StatisticRepository) = GetAllStatisticUseCase(repository)
    @[Singleton Provides]
    fun providesGetLastStatisticUseCase(repository: StatisticRepository) = GetLastStatisticUseCase(repository)

}