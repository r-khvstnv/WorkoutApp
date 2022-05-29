package com.rssll971.fitnessassistantapp.repo.db.repository

import com.rssll971.fitnessassistantapp.repo.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.repo.db.entity.BmiEntity
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