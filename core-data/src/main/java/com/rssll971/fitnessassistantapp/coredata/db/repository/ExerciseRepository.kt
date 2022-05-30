package com.rssll971.fitnessassistantapp.coredata.db.repository

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor(database: WorkoutDatabase) {
    private val dao: ExerciseDao = database.getExerciseDao()

    suspend fun insertExercise(exerciseEntity: ExerciseEntity){
        dao.insertExercise(exerciseEntity = exerciseEntity)
    }

    suspend fun updateExercise(exerciseEntity: ExerciseEntity){
        dao.updateExercise(exerciseEntity = exerciseEntity)
    }
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity){
        dao.deleteExercise(exerciseEntity = exerciseEntity)
    }

    fun getExerciseEntity(id: Int) = dao.getExercise(id = id)

    val allExerciseEntity: Flow<List<ExerciseEntity>> = dao.getAll()
}