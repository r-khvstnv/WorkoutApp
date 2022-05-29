package com.rssll971.fitnessassistantapp.core.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.core.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.core.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface BmiDao {
    @Insert
    suspend fun insertBmi(bmiEntity: BmiEntity)

    @Query("DELETE FROM ${Constants.BMI_TABLE}")
    suspend fun deleteAllRows()

    @Query("SELECT * FROM ${Constants.BMI_TABLE} ORDER BY ${Constants.ID}")
    fun getAll(): Flow<List<BmiEntity>>
}