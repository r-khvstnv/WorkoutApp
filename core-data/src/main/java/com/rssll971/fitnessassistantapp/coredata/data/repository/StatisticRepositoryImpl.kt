/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.repository

import com.rssll971.fitnessassistantapp.coredata.data.mapper.StatisticEntityToParamMapper
import com.rssll971.fitnessassistantapp.coredata.data.mapper.StatisticParamToEntityMapper
import com.rssll971.fitnessassistantapp.coredata.data.repository.source.StatisticSource
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class StatisticRepositoryImpl @Inject constructor(
    private val statisticSource: StatisticSource,
    private val entityToParamMapper: StatisticEntityToParamMapper,
    private val paramToEntityMapper: StatisticParamToEntityMapper,
): StatisticRepository {


    override suspend fun insertStatistic(statisticParam: StatisticParam){
        val entity = paramToEntityMapper.map(statisticParam)
        statisticSource.insertStatistic(statisticEntity = entity)
    }

    override suspend fun getAllStatistic(): Flow<List<StatisticParam>> =
        statisticSource.getAllStatistic().map {
            list ->
            list.map { entityToParamMapper.map(it) }
        }
}