/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/


package com.rssll971.fitnessassistantapp.coredata.data.repository.source

import com.rssll971.fitnessassistantapp.coredata.db.WorkoutDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.BmiEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BmiSource @Inject constructor(workoutDatabase: WorkoutDatabase) {
    private val dao: BmiDao = workoutDatabase.getBmiDao()

    suspend fun insertBmi(bmiEntity: BmiEntity){
        dao.insertBmiEntity(bmiEntity = bmiEntity)
    }
    suspend fun deleteAllBmi(){
        dao.deleteAllEntities()
    }
    fun getAllBmi(): Flow<List<BmiEntity>> = dao.getAllBmiEntities()
}