/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.repository

import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun insertExercise(exerciseParam: ExerciseParam)
    suspend fun updateExercise(exerciseParam: ExerciseParam)
    suspend fun deleteExercise(exerciseParam: ExerciseParam)

    fun getExerciseById(id: Int): Flow<ExerciseParam>
    fun getExerciseList(): Flow<List<ExerciseParam>>
    fun getExercisesByIdList(ids: List<Int>): Flow<List<ExerciseParam>>
}