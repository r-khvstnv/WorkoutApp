/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise

import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseByIdUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend operator fun invoke(id: Int): Flow<ExerciseParam> = repository.getExerciseById(id = id)
}