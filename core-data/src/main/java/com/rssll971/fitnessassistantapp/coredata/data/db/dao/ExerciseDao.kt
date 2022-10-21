/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExerciseEntity(exerciseEntity: ExerciseEntity)

    @Update
    suspend fun updateExerciseEntity(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteExerciseEntity(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} ORDER BY ${Constants.ID}")
    fun getAllExerciseEntities(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE ${Constants.ID} = :id ")
    fun getExerciseEntity(id: Int): Flow<ExerciseEntity>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE ${Constants.ID} IN (:idList)")
    fun getExerciseEntitiesById(idList: List<Int>): Flow<List<ExerciseEntity>>
}