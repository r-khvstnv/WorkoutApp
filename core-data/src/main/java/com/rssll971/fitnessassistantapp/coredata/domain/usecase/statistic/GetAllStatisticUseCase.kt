/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic

import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import javax.inject.Inject

class GetAllStatisticUseCase @Inject constructor(private val repository: StatisticRepository) {
    operator fun invoke() = repository.getAllStatistic()
}