/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/


package com.rssll971.fitnessassistantapp.coredata.data.repository.source

import com.rssll971.fitnessassistantapp.coredata.data.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.StatisticEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatisticSource @Inject constructor(workoutDatabase: WorkoutDatabase) {
    private val dao: StatisticDao = workoutDatabase.getStatisticDao()
    suspend fun insertStatistic(statisticEntity: StatisticEntity){
        dao.insertStatisticEntity(statisticEntity = statisticEntity)
    }
    fun getAllStatistic(): Flow<List<StatisticEntity>> = dao.getAllStatisticEntities()
}