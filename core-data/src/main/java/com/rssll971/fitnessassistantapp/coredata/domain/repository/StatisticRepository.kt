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
    suspend fun insertStatistic(statisticParam: StatisticParam)
    fun getAllStatistic(): Flow<List<StatisticParam>>
}