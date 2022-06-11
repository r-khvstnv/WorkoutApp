/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.addedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

class AddEditExerciseViewModel @Inject constructor(private val repository: ExerciseRepository): ViewModel() {
    /**Variables are used to receive notification
     * when a database operation completed successfully*/
    private var _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean> get() = _isSaved
    private var _isUpdated = MutableLiveData(false)
    val isUpdated: LiveData<Boolean> get() = _isUpdated
    private var _isDeleted = MutableLiveData(false)
    val isDeleted: LiveData<Boolean> get() = _isDeleted

    /**Variable notifies Fragment about target actions with exercise
     * If Fragment received safeArgs, then all actions with the exercise will be aimed at updating it.
     * Otherwise, Fragment will be used to add new exercise*/
    private var _isExerciseShouldBeUpdated = MutableLiveData(false)
    val isExerciseShouldBeUpdated: MutableLiveData<Boolean> get() = _isExerciseShouldBeUpdated

    private var _exerciseForUpdating: MutableLiveData<Exercise> = MutableLiveData()
    val exerciseForUpdating: LiveData<Exercise> get() = _exerciseForUpdating

    private var _imagePath = MutableLiveData("")
    val imagePath: LiveData<String> get() = _imagePath
    /**Serves to clear the storage of unused images,
     * that could be added during the save/update process*/
    private var oldImagePathsList = MutableLiveData<ArrayList<String>>(arrayListOf())

    /**Method request exercise from db using it's Id and after will assign:
     * - exercise itself
     * - _isExerciseShouldBeUpdated to true
     * - imagePath separately*/
    fun requestExerciseForUpdating(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getExercise(id = id).take(1).collect {
                exercise ->
                _exerciseForUpdating.postValue(exercise)
                _isExerciseShouldBeUpdated.postValue(true)
                withContext(Dispatchers.Main){
                    setImagePath(exercise.imagePath)
                }
            }
        }
    }

    fun addExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO){
            deleteAllUnnecessaryImages()
            repository.insertExercise(exercise = exercise)
            _isSaved.postValue(true)
        }
    }

    fun updateExercise(exercise: Exercise){
        viewModelScope.launch(Dispatchers.IO){
            deleteAllUnnecessaryImages()
            repository.updateExercise(exercise = exercise)
            _isUpdated.postValue(true)
        }
    }

    /**Method deletes exercise from database and all unused images,
     * that have been added before*/
    fun deleteExercise(){
        exerciseForUpdating.value?.let {
            exercise ->

            viewModelScope.launch(Dispatchers.IO){
                deleteAllUnnecessaryImages()
                repository.deleteExercise(exercise = exercise)
                _isDeleted.postValue(true)
            }
        }
    }

    fun setImagePath(path: String){
        _imagePath.value = path
        oldImagePathsList.value?.add(path)
    }

    /**Method deletes only current Image from internal directory*/
    fun deleteCurrentImage(){
        imagePath.value?.let {
            path ->
            if (path.isNotEmpty()){
                viewModelScope.launch(Dispatchers.IO){
                    val file = File(path)
                    try {
                        file.delete()

                        withContext(Dispatchers.Main){
                            oldImagePathsList.value?.removeLast()
                            setImagePath("")
                        }
                    } catch (e: IOException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    /**Method deletes all images added during the save/update process */
    private suspend fun deleteAllUnnecessaryImages(){
        oldImagePathsList.value?.let {
            list ->

            if (list.size > 1){
                list.removeLast()

                for (path in list){
                    if (path.isNotEmpty()){
                        val file = File(path)
                        try {
                            file.delete()
                        } catch (e: IOException){
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }
}