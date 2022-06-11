/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.coredata.models.Statistic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OptionsViewModel @Inject constructor(
    private val repoStatistic: StatisticRepository,
    private val repoExercise: ExerciseRepository
): ViewModel() {
    private val defaultTimeStep: Int = 30

    private var _restTime: MutableLiveData<Int> = MutableLiveData(30)
    val restTime: LiveData<Int> get() = _restTime
    private var _exerciseTime: MutableLiveData<Int> = MutableLiveData(30)
    val exerciseTime: LiveData<Int> get() = _exerciseTime
    private var _isVoiceEnabled = MutableLiveData(false)

    private var _isStatisticAdded = MutableLiveData(false)
    val isStatisticAdded: LiveData<Boolean> get() = _isStatisticAdded

    val exerciseList: LiveData<List<Exercise>> = repoExercise.getExerciseList().asLiveData()


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


    /**Method is used in OptionSecondFragment
     * Using customized by user data, it adds entity to repository.
     * Later, the current instance of Statistic will be used in the WorkoutFragment.
     * Thus, using the statisticRepository, configured data transfer between
     * OptionSecondFragment -> WorkoutFragment*/
    fun setupStatistic(list: List<Int>, dateInMillis: Long){
        viewModelScope.launch(Dispatchers.IO){
            val statistic = Statistic(
                dateInMillis,
                restTime.value!!,
                exerciseTime.value!!,
                _isVoiceEnabled.value!!,
                list,
                0
            )

            repoStatistic.insertStatistic(statistic = statistic)
            _isStatisticAdded.postValue(true)
        }
    }
}