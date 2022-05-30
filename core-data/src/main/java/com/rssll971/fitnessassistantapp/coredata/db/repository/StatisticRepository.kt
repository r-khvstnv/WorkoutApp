package com.rssll971.fitnessassistantapp.coredata.db.repository

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.StatisticEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatisticRepository @Inject constructor(database: WorkoutDatabase) {
    private val dao: StatisticDao = database.getStatisticDao()

    suspend fun insertStatistic(statisticEntity: StatisticEntity){
        dao.insertStatistic(statisticEntity = statisticEntity)
    }

    suspend fun deleteStatistic(statisticEntity: StatisticEntity){
        dao.deleteStatistic(statisticEntity = statisticEntity)
    }

    suspend fun deleteAll(){
        dao.deleteAllRows()
    }

    val allStatisticEntities: Flow<List<StatisticEntity>> = dao.getAll()
}