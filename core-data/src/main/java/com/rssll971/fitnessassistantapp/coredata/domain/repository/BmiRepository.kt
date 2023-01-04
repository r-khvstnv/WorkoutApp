/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.repository

import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import kotlinx.coroutines.flow.Flow

interface BmiRepository {
    /** Method adds [BmiParam] data to the source*/
    suspend fun insertBmi(bmiParam: BmiParam)
    /** Method deletes all Bmi records in the source*/
    suspend fun deleteAllBmi()
    /** Method returns all [BmiParam] records from the source*/
    fun getBmiList(): Flow<List<BmiParam>>
}