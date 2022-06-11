/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.db.repository

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepository @Inject constructor(database: WorkoutDatabase) {
    private val dao: ExerciseDao = database.getExerciseDao()

    suspend fun insertExercise(exercise: Exercise){
        dao.insertExerciseEntity(ExerciseEntity.fromExercise(exercise))
    }

    suspend fun updateExercise(exercise: Exercise){
        dao.updateExerciseEntity(ExerciseEntity.fromExercise(exercise))
    }
    suspend fun deleteExercise(exercise: Exercise){
        dao.deleteExerciseEntity(ExerciseEntity.fromExercise(exercise))
    }

    fun getExercise(id: Int): Flow<Exercise>{
        return dao.getExerciseEntity(id = id).map {
            it.toExercise()
        }
    }

    fun getExerciseList(): Flow<List<Exercise>> {
        return dao.getAllExerciseEntities().map { list ->
            list.map { it.toExercise() }
        }
    }

    fun getExerciseListById(idList: List<Int>): Flow<List<Exercise>>{
        return dao.getExerciseEntitiesById(idList = idList).map { list ->
            list.map { it.toExercise() }
        }
    }
}