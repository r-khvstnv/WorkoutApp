/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.all

import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.DeleteExerciseUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetAllExercisesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import javax.inject.Inject

internal class AllExercisesViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {
    val allExercises: LiveData<List<ExerciseParam>> = getAllExercisesUseCase.invoke().asLiveData()

    /**Method deletes exerciseParam from database and it's corresponding image*/
    fun requestExerciseDeleting(exerciseParam: ExerciseParam){
        viewModelScope.launch(Dispatchers.IO) {
            deleteImage(exerciseParam.imagePath)
            deleteExerciseUseCase.invoke(param = exerciseParam)
        }
    }

    private fun deleteImage(path: String){
        val file = File(path)
        try {
            file.delete()
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}