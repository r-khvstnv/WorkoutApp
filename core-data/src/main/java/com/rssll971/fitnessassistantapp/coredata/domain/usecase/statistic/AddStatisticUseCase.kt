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
import javax.inject.Inject

class AddStatisticUseCase @Inject constructor(private val repository: StatisticRepository) {
    suspend operator fun invoke(param: StatisticParam) = repository.insertStatistic(param)
}