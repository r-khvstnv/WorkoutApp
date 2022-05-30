package com.rssll971.fitnessassistantapp.coredata.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface BmiDao {
    @Insert
    suspend fun insertBmiEntity(bmiEntity: BmiEntity)

    @Query("DELETE FROM ${Constants.BMI_TABLE}")
    suspend fun deleteAllEntities()

    @Query("SELECT * FROM ${Constants.BMI_TABLE} ORDER BY ${Constants.ID}")
    fun getAllBmiEntities(): Flow<List<BmiEntity>>
}