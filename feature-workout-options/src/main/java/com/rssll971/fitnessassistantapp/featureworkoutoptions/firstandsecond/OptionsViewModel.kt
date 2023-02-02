/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetAllExercisesUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject
//todo back internal property
class OptionsViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val addStatisticUseCase: AddStatisticUseCase
): ViewModel() {
    private val defaultTimeStep: Int = 30

    private var _restTime: MutableLiveData<Int> = MutableLiveData(60)
    val restTime: LiveData<Int> get() = _restTime
    private var _exerciseTime: MutableLiveData<Int> = MutableLiveData(60)
    val exerciseTime: LiveData<Int> get() = _exerciseTime
    private var _isVoiceEnabled = MutableLiveData(false)

    private var _isStatisticAdded = MutableLiveData(false)
    val isStatisticAdded: LiveData<Boolean> get() = _isStatisticAdded

    //todo change to correct impl
    //val exerciseParamList: LiveData<List<ExerciseParam>> = getAllExercisesUseCase.invoke().asLiveData()
    val exerciseParamList: LiveData<List<ExerciseParam>> = flowOf(listOf(
        ExerciseParam(
            "exercise",
            imagePath = "somePath",
            description = "desc",
            id = 8
        )
    )).asLiveData()


    fun setVoiceAvailability(enable: Boolean){
        _isVoiceEnabled.postValue(enable)
    }

    /**Next methods increase/decrease time by default value*/
    fun increaseRestTime(){
        _restTime.value = _restTime.value!! + defaultTimeStep
    }
    fun decreaseRestTime(){
        if (_restTime.value!! > 30){
            _restTime.value = _restTime.value!! - defaultTimeStep
        }
    }
    fun increaseExerciseTime(){
        _exerciseTime.value = _exerciseTime.value!! + defaultTimeStep
    }
    fun decreaseExerciseTime(){
        if (_exerciseTime.value!! > 30){
            _exerciseTime.value = _exerciseTime.value!! - defaultTimeStep
        }
    }


    /**
     * Method is used to add Statistic to the source.
     * */
    fun setupStatistic(list: List<Int>, dateInMillis: Long){
        viewModelScope.launch(Dispatchers.IO){
            val statisticParam = StatisticParam(
                dateInMillis,
                restTime.value!!,
                exerciseTime.value!!,
                _isVoiceEnabled.value!!,
                list,
                0
            )

            addStatisticUseCase.invoke(param = statisticParam)
            _isStatisticAdded.postValue(true)
        }
    }
}