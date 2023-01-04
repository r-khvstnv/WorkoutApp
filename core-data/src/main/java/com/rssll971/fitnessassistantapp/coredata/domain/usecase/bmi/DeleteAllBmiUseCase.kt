/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi

import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import javax.inject.Inject

class DeleteAllBmiUseCase @Inject constructor(private val repository: BmiRepository) {
    suspend operator fun invoke(){
        repository.deleteAllBmi()
    }
}