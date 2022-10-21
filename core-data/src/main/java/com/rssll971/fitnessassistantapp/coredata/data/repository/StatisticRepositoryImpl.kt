/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.repository

import com.rssll971.fitnessassistantapp.coredata.data.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatisticRepositoryImpl @Inject constructor(database: WorkoutDatabase) {
    private val dao: StatisticDao = database.getStatisticDao()

    suspend fun insertStatistic(statisticParam: StatisticParam){
        dao.insertStatisticEntity(StatisticEntity.fromStatistic(statisticParam = statisticParam))
    }

    suspend fun deleteStatistic(statisticParam: StatisticParam){
        dao.deleteStatisticEntity(StatisticEntity.fromStatistic(statisticParam = statisticParam))
    }

    suspend fun deleteAllStatistic(){
        dao.deleteAllStatisticEntities()
    }

    fun getStatisticList(): Flow<List<StatisticParam>>{
        return dao.getAllStatisticEntities().map { list ->
            list.map { it.toStatistic() }
        }
    }

    fun getLastStatistic(): Flow<StatisticParam>{
        return dao.getLastStatisticEntityRow().map { it.toStatistic() }
    }
}