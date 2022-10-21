/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.repository

import com.rssll971.fitnessassistantapp.coredata.data.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(database: WorkoutDatabase) {
    private val dao: ExerciseDao = database.getExerciseDao()

    suspend fun insertExercise(exerciseParam: ExerciseParam){
        dao.insertExerciseEntity(ExerciseEntity.fromExercise(exerciseParam))
    }

    suspend fun updateExercise(exerciseParam: ExerciseParam){
        dao.updateExerciseEntity(ExerciseEntity.fromExercise(exerciseParam))
    }
    suspend fun deleteExercise(exerciseParam: ExerciseParam){
        dao.deleteExerciseEntity(ExerciseEntity.fromExercise(exerciseParam))
    }

    fun getExercise(id: Int): Flow<ExerciseParam>{
        return dao.getExerciseEntity(id = id).map {
            it.toExercise()
        }
    }

    fun getExerciseList(): Flow<List<ExerciseParam>> {
        return dao.getAllExerciseEntities().map { list ->
            list.map { it.toExercise() }
        }
    }

    fun getExerciseListById(idList: List<Int>): Flow<List<ExerciseParam>>{
        return dao.getExerciseEntitiesById(idList = idList).map { list ->
            list.map { it.toExercise() }
        }
    }
}