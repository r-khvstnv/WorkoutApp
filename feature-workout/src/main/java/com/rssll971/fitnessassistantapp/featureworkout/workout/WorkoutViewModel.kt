/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkout.workout

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetExercisesByIdListUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetLastStatisticUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class WorkoutViewModel @Inject constructor(
    private val getExercisesByIdListUseCase: GetExercisesByIdListUseCase,
    private val getLastStatisticUseCase: GetLastStatisticUseCase
) : ViewModel() {
    //Store all necessary information about current workout
    val workoutSettings: LiveData<StatisticParam> = getLastStatisticUseCase.invoke().asLiveData()

    private var exerciseParamList: MutableLiveData<List<ExerciseParam>> = MutableLiveData()
    //Handle exerciseLayout visibility State
    private var _isExerciseLayoutShouldBeShown: MutableLiveData<Boolean> = MutableLiveData()
    val isExerciseLayoutShouldBeShown: LiveData<Boolean> get() = _isExerciseLayoutShouldBeShown
    //Progress timers for rest/exercise
    private var _restTimerProgress: MutableLiveData<Long> = MutableLiveData()
    val restTimerProgress: LiveData<Long> get() = _restTimerProgress
    private var _exerciseTimerProgress: MutableLiveData<Long> = MutableLiveData()
    val exerciseTimerProgress: LiveData<Long> get() = _exerciseTimerProgress

    private var _currentExercisePosition = MutableLiveData(-1)
    val currentExercisePosition: LiveData<Int> get() = _currentExercisePosition

    private var _currentExerciseParam: MutableLiveData<ExerciseParam> = MutableLiveData()
    val currentExerciseParam: LiveData<ExerciseParam> get() = _currentExerciseParam

    private var _isWorkoutFinished = MutableLiveData(false)
    val isWorkoutFinished: LiveData<Boolean> get() = _isWorkoutFinished


    /**Method requests exercises from database.
     * After will assign them to exerciseParamList and call startRestOrFinishWorkout() method*/
    fun requestExercises(ids: List<Int>){
        viewModelScope.launch(Dispatchers.IO){
            getExercisesByIdListUseCase.invoke(ids = ids).take(1).collect {
                list ->
                exerciseParamList.postValue(list)
                withContext(Dispatchers.Main){
                    startRestOrFinishWorkout()
                }
            }
        }
    }


    /**Method launch restTimer using value from workoutSettings.
     * onFinish request startExercise method*/
    private fun setRestTimer(){
        object : CountDownTimer((workoutSettings.value!!.restDuration * 1000).toLong(), 1000){
            override fun onTick(timeL: Long) {
                _restTimerProgress.postValue(timeL)
            }
            override fun onFinish() {
                startExercise()
            }
        }.start()
    }

    /**Method launch exerciseTimer using value from workoutSettings.
     * onFinish request startRestOrFinishWorkout method*/
    private fun setExerciseTimer(){
        object : CountDownTimer((workoutSettings.value!!.exerciseDuration * 1000).toLong(), 1000){
            override fun onTick(timeL: Long) {
                _exerciseTimerProgress.postValue(timeL)
            }
            override fun onFinish() {
                startRestOrFinishWorkout()
            }
        }.start()
    }


    /**Method increment currentExercisePosition and after
     * update currentExerciseParam instance using exerciseParamList and new position*/
    private fun updateCurrentExercise(){
        _currentExercisePosition.value = _currentExercisePosition.value!! + 1
        _currentExerciseParam.value = exerciseParamList.value!![_currentExercisePosition.value!!]
    }


    /**Method firstly checks that currentExercisePosition is not out of bounds of exerciseParamList
     * true -> requests updateCurrentExercise/setRestTimer methods
     *         changes state of _isExerciseLayoutShouldBeShown to true
     * false -> changes state of _isWorkoutFinished to true*/
    private fun startRestOrFinishWorkout(){
        if (_currentExercisePosition.value!! < exerciseParamList.value!!.size - 1){
            updateCurrentExercise()
            setRestTimer()
            _isExerciseLayoutShouldBeShown.postValue(false)
        } else{
            _isWorkoutFinished.postValue(true)
        }
    }

    /**Method starts exercise.
     * It changes state of _isExerciseLayoutShouldBeShown to false and calls setExerciseTimer method*/
    private fun startExercise(){
        _isExerciseLayoutShouldBeShown.postValue(true)
        setExerciseTimer()
    }
}