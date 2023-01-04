/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
internal interface StatisticDao {
    @Insert
    suspend fun insertStatisticEntity(statisticEntity: StatisticEntity)

    @Delete
    suspend fun deleteStatisticEntity(statisticEntity: StatisticEntity)

    @Query("DELETE FROM ${Constants.STATISTIC_TABLE}")
    suspend fun deleteAllStatisticEntities()

    @Query("SELECT * FROM ${Constants.STATISTIC_TABLE} ORDER BY ${Constants.ID}")
    fun getAllStatisticEntities(): Flow<List<StatisticEntity>>

    @Query("SELECT * FROM ${Constants.STATISTIC_TABLE} ORDER BY ${Constants.ID} DESC LIMIT 1")
    fun getLastStatisticEntityRow(): Flow<StatisticEntity>
}