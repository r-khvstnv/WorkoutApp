package com.rssll971.fitnessassistantapp.coredata.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insertExercise(exerciseEntity: ExerciseEntity)

    @Update
    suspend fun updateExercise(exerciseEntity: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} ORDER BY ${Constants.ID}")
    fun getAll(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM ${Constants.EXERCISE_TABLE} WHERE ${Constants.ID} = :id ")
    fun getExercise(id: Int): Flow<ExerciseEntity>
}