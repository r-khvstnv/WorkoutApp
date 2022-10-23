/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.repository

import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import kotlinx.coroutines.flow.Flow

interface StatisticRepository {
    /**Method add [statisticParam] data to the source*/
    suspend fun insertStatistic(statisticParam: StatisticParam)
    /** Method returns all [StatisticParam] records from the source*/
    fun getAllStatistic(): Flow<List<StatisticParam>>
    /** Method returns last [StatisticParam] records from the source*/
    fun getLastStatistic(): Flow<StatisticParam>
}