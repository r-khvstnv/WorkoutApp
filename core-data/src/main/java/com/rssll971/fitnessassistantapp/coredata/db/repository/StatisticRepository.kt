package com.rssll971.fitnessassistantapp.coredata.db.repository

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.models.Statistic
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StatisticRepository @Inject constructor(database: WorkoutDatabase) {
    private val dao: StatisticDao = database.getStatisticDao()

    suspend fun insertStatistic(statistic: Statistic){
        dao.insertStatisticEntity(StatisticEntity.fromStatistic(statistic = statistic))
    }

    suspend fun deleteStatistic(statistic: Statistic){
        dao.deleteStatisticEntity(StatisticEntity.fromStatistic(statistic = statistic))
    }

    suspend fun deleteAllStatistic(){
        dao.deleteAllStatisticEntities()
    }

    fun getStatisticList(): Flow<List<Statistic>>{
        return dao.getAllStatisticEntities().map { list ->
            list.map { it.toStatistic() }
        }
    }
}