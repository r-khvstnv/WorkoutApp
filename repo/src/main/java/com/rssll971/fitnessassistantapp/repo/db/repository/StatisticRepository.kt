package com.rssll971.fitnessassistantapp.repo.db.repository

import com.rssll971.fitnessassistantapp.repo.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.repo.db.entity.StatisticEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StatisticRepository @Inject constructor(private val dao: StatisticDao) {

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