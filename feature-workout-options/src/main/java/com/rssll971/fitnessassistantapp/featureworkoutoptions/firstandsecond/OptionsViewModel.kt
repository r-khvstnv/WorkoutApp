package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
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
    private var _selectedExercisesIdList: MutableLiveData<List<Int>> = MutableLiveData(listOf())

    val exerciseList: LiveData<List<Exercise>> = repoExercise.getExerciseList().asLiveData()


    fun setVoiceAvailability(enable: Boolean){
        _isVoiceEnabled.postValue(enable)
    }

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

    fun setSelectedIdsList(list: List<Int>){
        _selectedExercisesIdList.value = list
    }
}