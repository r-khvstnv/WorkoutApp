package com.rssll971.fitnessassistantapp.coredata.db.repository

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.models.Bmi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BmiRepository @Inject constructor(database: WorkoutDatabase) {
    private val dao: BmiDao = database.getBmiDao()

    suspend fun insertBmi(bmi: Bmi){
        dao.insertBmiEntity(BmiEntity.fromBmi(bmi = bmi))
    }

    suspend fun deleteAllBmi(){
        dao.deleteAllEntities()
    }

    fun getBmiList(): Flow<List<Bmi>>{
        return dao.getAllBmiEntities().map { list ->
            list.map { it.toBmi() }
        }
    }
}