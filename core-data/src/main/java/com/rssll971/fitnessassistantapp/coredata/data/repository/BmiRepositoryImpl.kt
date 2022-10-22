/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.repository

import com.rssll971.fitnessassistantapp.coredata.data.mapper.BmiEntityToParamMapper
import com.rssll971.fitnessassistantapp.coredata.data.mapper.BmiParamToEntityMapper
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.BmiSource
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class BmiRepositoryImpl @Inject constructor(
    private val bmiSource: BmiSource,
    private val entityToParamMapper: BmiEntityToParamMapper,
    private val paramToEntityMapper: BmiParamToEntityMapper
): BmiRepository {

    override suspend fun insertBmi(bmiParam: BmiParam){
        val entity = paramToEntityMapper.map(bmiParam)
        bmiSource.insertBmi(bmiEntity = entity)
    }

    override suspend fun deleteAllBmi() = bmiSource.deleteAllBmi()

    override fun getBmiList(): Flow<List<BmiParam>> = bmiSource.getAllBmi().map { list ->
        list.map { entityToParamMapper.map(it) }
    }
}