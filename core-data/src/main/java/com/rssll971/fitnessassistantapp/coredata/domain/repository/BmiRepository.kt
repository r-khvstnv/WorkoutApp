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
    suspend fun insertBmi(bmiParam: BmiParam)
    suspend fun deleteAllBmi()
    fun getBmiList(): Flow<List<BmiParam>>
}