package com.rssll971.fitnessassistantapp.coredata.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.db.entity.ExerciseEntity
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
}