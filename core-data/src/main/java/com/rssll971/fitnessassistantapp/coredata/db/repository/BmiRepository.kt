package com.rssll971.fitnessassistantapp.coredata.db.repository

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.BmiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BmiRepository @Inject constructor(database: WorkoutDatabase) {
    private val dao: BmiDao = database.getBmiDao()

    suspend fun insertBmi(bmiEntity: BmiEntity){
        dao.insertBmi(bmiEntity = bmiEntity)
    }

    suspend fun deleteAllBmi(){
        dao.deleteAllRows()
    }

    val allBmiEntity: Flow<List<BmiEntity>> = dao.getAll()
}