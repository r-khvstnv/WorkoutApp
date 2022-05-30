package com.rssll971.fitnessassistantapp.coredata.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticDao {
    @Insert
    suspend fun insertStatistic(statisticEntity: StatisticEntity)

    @Delete
    suspend fun deleteStatistic(statisticEntity: StatisticEntity)

    @Query("DELETE FROM ${Constants.STATISTIC_TABLE}")
    suspend fun deleteAllRows()

    @Query("SELECT * FROM ${Constants.STATISTIC_TABLE} ORDER BY ${Constants.ID}")
    fun getAll(): Flow<List<StatisticEntity>>
}