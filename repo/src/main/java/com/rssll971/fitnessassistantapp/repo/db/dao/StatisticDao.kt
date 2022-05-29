package com.rssll971.fitnessassistantapp.repo.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.repo.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.repo.utils.Constants
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