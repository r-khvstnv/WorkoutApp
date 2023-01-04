/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic

import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastStatisticUseCase @Inject constructor(private val repository: StatisticRepository) {
    operator fun invoke(): Flow<StatisticParam> = repository.getLastStatistic()
}