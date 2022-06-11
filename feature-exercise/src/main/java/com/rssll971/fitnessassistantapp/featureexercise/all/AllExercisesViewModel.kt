/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.all

import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class AllExercisesViewModel @Inject constructor(private val repository: ExerciseRepository) : ViewModel() {
    val allExercises: LiveData<List<Exercise>> = repository.getExerciseList().asLiveData()

    /**Method deletes exercise from database and it's corresponding image*/
    fun requestExerciseDeleting(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO) {
            deleteImage(exercise.imagePath)
            repository.deleteExercise(exercise = exercise)
        }
    }

    private suspend fun deleteImage(path: String){
        val file = File(path)
        try {
            file.delete()
        } catch (e: IOException){
            e.printStackTrace()
        }
    }
}