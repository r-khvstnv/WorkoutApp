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
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.AddExerciseUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.DeleteExerciseUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetExerciseByIdUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.UpdateExerciseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException
import javax.inject.Inject

internal class AddEditExerciseViewModel @Inject constructor(
    private val addExerciseUseCase: AddExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase
): ViewModel() {
    /**Variables are used to receive notification
     * when a database operation completed successfully*/
    private var _isSaved = MutableLiveData(false)
    val isSaved: LiveData<Boolean> get() = _isSaved
    private var _isUpdated = MutableLiveData(false)
    val isUpdated: LiveData<Boolean> get() = _isUpdated
    private var _isDeleted = MutableLiveData(false)
    val isDeleted: LiveData<Boolean> get() = _isDeleted

    /*
     * Variable notifies Fragment about target actions with exercise
     * If Fragment received safeArgs, then all actions with the exercise will be aimed at updating it.
     * Otherwise, Fragment will be used to add new exercise
     * */
    private var _isExerciseShouldBeUpdated = MutableLiveData(false)
    val isExerciseShouldBeUpdated: MutableLiveData<Boolean> get() = _isExerciseShouldBeUpdated

    private var _exerciseParamForUpdating: MutableLiveData<ExerciseParam> = MutableLiveData()
    val exerciseParamForUpdating: LiveData<ExerciseParam> get() = _exerciseParamForUpdating

    private var _imagePath = MutableLiveData("")
    val imagePath: LiveData<String> get() = _imagePath
    /**
     * Serves to clear the storage of unused images,
     * that could be added during the save/update process
     * */
    private var oldImagePathsList = MutableLiveData<ArrayList<String>>(arrayListOf())

    /**
     * Method request Exercise record from source using it's [id] and after will be:
     * - [_exerciseParamForUpdating] assigned
     * - [_isExerciseShouldBeUpdated] changed to true
     * - [setImagePath] called
     * */
    fun requestExerciseForUpdating(id: Int){
        viewModelScope.launch(Dispatchers.IO){
            getExerciseByIdUseCase.invoke(id = id).take(1).collect {
                exercise ->
                _exerciseParamForUpdating.postValue(exercise)
                _isExerciseShouldBeUpdated.postValue(true)
                withContext(Dispatchers.Main){
                    setImagePath(exercise.imagePath)
                }
            }
        }
    }

    /**
     * Method adds [exerciseParam] record to the source.
     * */
    fun addExercise(exerciseParam: ExerciseParam){
        viewModelScope.launch(Dispatchers.IO){
            deleteAllUnnecessaryImages()
            addExerciseUseCase.invoke(param = exerciseParam)
            _isSaved.postValue(true)
        }
    }
    /**
     * Method updates [exerciseParam] record in the source.
     * */
    fun updateExercise(exerciseParam: ExerciseParam){
        viewModelScope.launch(Dispatchers.IO){
            deleteAllUnnecessaryImages()
            updateExerciseUseCase.invoke(param = exerciseParam)
            _isUpdated.postValue(true)
        }
    }
    /**
     * Method deletes Exercise ([_exerciseParamForUpdating]) from the source and calls [deleteAllUnnecessaryImages].
     * */
    fun deleteExercise(){
        _exerciseParamForUpdating.value?.let {
            exercise ->

            viewModelScope.launch(Dispatchers.IO){
                deleteAllUnnecessaryImages()
                deleteExerciseUseCase.invoke(param = exercise)
                _isDeleted.postValue(true)
            }
        }
    }

    /**
     * Method sets [path] to [_imagePath] and add it to the [oldImagePathsList].
     * */
    fun setImagePath(path: String){
        _imagePath.postValue(path)
        oldImagePathsList.value?.add(path)
    }

    /**
     * Method deletes current image only and it's mention in instances.
     *
     * Will be deleted:
     * - image itself
     * - path in [oldImagePathsList]
     * - and [setImagePath] will be changed
     * */
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

    /**
     * Method deletes all unused images, using paths in the [oldImagePathsList].
     * */
    private fun deleteAllUnnecessaryImages(){
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