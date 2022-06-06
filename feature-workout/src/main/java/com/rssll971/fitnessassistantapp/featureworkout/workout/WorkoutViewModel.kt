package com.rssll971.fitnessassistantapp.featureworkout.workout

import android.os.CountDownTimer
import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.coredata.models.Statistic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorkoutViewModel @Inject constructor(
    private val repoStatistic: StatisticRepository,
    private val repoExercise: ExerciseRepository
) : ViewModel() {
    val workoutSettings: LiveData<Statistic> = repoStatistic.getLastStatistic().asLiveData()
    private var exerciseList: MutableLiveData<List<Exercise>> = MutableLiveData()

    private var _isExerciseLayoutShouldBeShown: MutableLiveData<Boolean> = MutableLiveData()
    val isExerciseLayoutShouldBeShown: LiveData<Boolean> get() = _isExerciseLayoutShouldBeShown

    private var _restTimerProgress: MutableLiveData<Long> = MutableLiveData()
    val restTimerProgress: LiveData<Long> get() = _restTimerProgress
    private var _exerciseTimerProgress: MutableLiveData<Long> = MutableLiveData()
    val exerciseTimerProgress: LiveData<Long> get() = _exerciseTimerProgress

    private var _currentExercisePosition = MutableLiveData(-1)
    val currentExercisePosition: LiveData<Int> get() = _currentExercisePosition

    private var _currentExercise: MutableLiveData<Exercise> = MutableLiveData()
    val currentExercise: LiveData<Exercise> get() = _currentExercise

    private var _isWorkoutFinished = MutableLiveData(false)
    val isWorkoutFinished: LiveData<Boolean> get() = _isWorkoutFinished

    fun requestExercises(ids: List<Int>){
        viewModelScope.launch(Dispatchers.IO){
            repoExercise.getExerciseListById(ids).take(1).collect {
                list ->
                exerciseList.postValue(list)
                withContext(Dispatchers.Main){
                    startRestOrFinishWorkout()
                }
            }
        }
    }

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

    private fun updateCurrentExercise(){
        _currentExercisePosition.value = _currentExercisePosition.value!! + 1
        _currentExercise.value = exerciseList.value!![_currentExercisePosition.value!!]
    }

    private fun startRestOrFinishWorkout(){
        if (_currentExercisePosition.value!! < exerciseList.value!!.size - 1){
            updateCurrentExercise()
            setRestTimer()
            _isExerciseLayoutShouldBeShown.postValue(false)
        } else{
            _isWorkoutFinished.postValue(true)
        }
    }
    private fun startExercise(){
        _isExerciseLayoutShouldBeShown.postValue(true)
        setExerciseTimer()
    }
}