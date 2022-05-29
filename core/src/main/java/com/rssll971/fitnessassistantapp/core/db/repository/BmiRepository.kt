package com.rssll971.fitnessassistantapp.core.db.repository

import com.rssll971.fitnessassistantapp.core.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.core.db.entity.BmiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BmiRepository @Inject constructor(private val dao: BmiDao) {

    suspend fun insertBmi(bmiEntity: BmiEntity){
        dao.insertBmi(bmiEntity = bmiEntity)
    }

    suspend fun deleteAllBmi(){
        dao.deleteAllRows()
    }

    val allBmiEntity: Flow<List<BmiEntity>> = dao.getAll()
}