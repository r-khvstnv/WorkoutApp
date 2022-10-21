/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi

import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBmiUseCase @Inject constructor(private val repository: BmiRepository) {
    operator fun invoke(): Flow<List<BmiParam>> = repository.getBmiList()
}