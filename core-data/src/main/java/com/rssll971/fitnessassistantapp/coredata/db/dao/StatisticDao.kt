package com.rssll971.fitnessassistantapp.coredata.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {
    @Insert
    suspend fun insertStatisticEntity(statisticEntity: StatisticEntity)

    @Delete
    suspend fun deleteStatisticEntity(statisticEntity: StatisticEntity)

    @Query("DELETE FROM ${Constants.STATISTIC_TABLE}")
    suspend fun deleteAllStatisticEntities()

    @Query("SELECT * FROM ${Constants.STATISTIC_TABLE} ORDER BY ${Constants.ID}")
    fun getAllStatisticEntities(): Flow<List<StatisticEntity>>
}