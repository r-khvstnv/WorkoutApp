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
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BmiRepositoryImpl @Inject constructor(database: WorkoutDatabase) {
    private val dao: BmiDao = database.getBmiDao()

    suspend fun insertBmi(bmiParam: BmiParam){
        dao.insertBmiEntity(BmiEntity.fromBmi(bmiParam = bmiParam))
    }

    suspend fun deleteAllBmi(){
        dao.deleteAllEntities()
    }

    fun getBmiList(): Flow<List<BmiParam>>{
        return dao.getAllBmiEntities().map { list ->
            list.map { it.toBmi() }
        }
    }
}