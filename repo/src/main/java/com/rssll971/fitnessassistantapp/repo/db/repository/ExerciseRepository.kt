package com.rssll971.fitnessassistantapp.repo.db.repository

import com.rssll971.fitnessassistantapp.repo.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.repo.db.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val dao: ExerciseDao) {

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