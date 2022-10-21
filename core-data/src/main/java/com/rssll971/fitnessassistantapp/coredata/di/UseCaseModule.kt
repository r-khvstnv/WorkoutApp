/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.di

import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.AddBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.DeleteAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.GetAllBmiUseCase
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
}