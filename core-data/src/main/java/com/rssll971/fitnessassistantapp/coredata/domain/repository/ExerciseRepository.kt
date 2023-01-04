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
    /**Method adds [exerciseParam] data to the source*/
    suspend fun insertExercise(exerciseParam: ExerciseParam)
    /**Method updates [exerciseParam] data to the source*/
    suspend fun updateExercise(exerciseParam: ExerciseParam)
    /**Method deletes [exerciseParam] data to the source*/
    suspend fun deleteExercise(exerciseParam: ExerciseParam)

    /**Method returns [ExerciseParam] by requested [id]*/
    fun getExerciseById(id: Int): Flow<ExerciseParam>
    /** Method returns all [ExerciseParam] records from the source*/
    fun getExerciseList(): Flow<List<ExerciseParam>>
    /** Method returns all [ExerciseParam] records, that matched the [ids] */
    fun getExercisesByIdList(ids: List<Int>): Flow<List<ExerciseParam>>
}