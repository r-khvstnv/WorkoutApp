/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise

import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesByIdListUseCase @Inject constructor(private val repository: ExerciseRepository) {
    operator fun invoke(ids: List<Int>) = repository.getExercisesByIdList(ids = ids)
}