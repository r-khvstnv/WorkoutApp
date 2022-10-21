/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/


package com.rssll971.fitnessassistantapp.coredata.data.repository.source

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseSource @Inject constructor(workoutDatabase: WorkoutDatabase) {
    private val dao: ExerciseDao = workoutDatabase.getExerciseDao()

    suspend fun insertExercise(exerciseEntity: ExerciseEntity){
        dao.insertExerciseEntity(exerciseEntity = exerciseEntity)
    }
    suspend fun updateExercise(exerciseEntity: ExerciseEntity){
        dao.updateExerciseEntity(exerciseEntity = exerciseEntity)
    }
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity){
        dao.deleteExerciseEntity(exerciseEntity = exerciseEntity)
    }

    fun getExerciseById(id: Int): Flow<ExerciseEntity> = dao.getExerciseEntity(id = id)
    fun getAllExercises(): Flow<List<ExerciseEntity>> = dao.getAllExerciseEntities()
    fun getExercisesByIdList(ids: List<Int>): Flow<List<ExerciseEntity>> = dao.getExerciseEntitiesById(idList = ids)
}