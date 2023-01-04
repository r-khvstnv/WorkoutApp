/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.repository

import com.rssll971.fitnessassistantapp.coredata.data.mapper.ExerciseEntityToParamMapper
import com.rssll971.fitnessassistantapp.coredata.data.mapper.ExerciseParamToEntityMapper
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.ExerciseSource
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseSource: ExerciseSource,
    private val entityToParamMapper: ExerciseEntityToParamMapper,
    private val paramToEntityMapper: ExerciseParamToEntityMapper,
): ExerciseRepository {

    override suspend fun insertExercise(exerciseParam: ExerciseParam){
        val entity = paramToEntityMapper.map(exerciseParam)
        exerciseSource.insertExercise(exerciseEntity = entity)
    }

    override suspend fun updateExercise(exerciseParam: ExerciseParam){
        val entity = paramToEntityMapper.map(exerciseParam)
        exerciseSource.updateExercise(exerciseEntity = entity)
    }
    override suspend fun deleteExercise(exerciseParam: ExerciseParam){
        val entity = paramToEntityMapper.map(exerciseParam)
        exerciseSource.deleteExercise(exerciseEntity = entity)
    }

    override fun getExerciseById(id: Int): Flow<ExerciseParam> =
        exerciseSource.getExerciseById(id = id).map { entityToParamMapper.map(it) }



    override fun getExerciseList(): Flow<List<ExerciseParam>> =
        exerciseSource.getAllExercises().map {
            list ->
            list.map { entityToParamMapper.map(it) }
        }

    override fun getExercisesByIdList(ids: List<Int>): Flow<List<ExerciseParam>> =
        exerciseSource.getExercisesByIdList(ids = ids).map {
            list ->
            list.map { entityToParamMapper.map(it) }
        }
}