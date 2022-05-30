package com.rssll971.fitnessassistantapp.featureexercise.all

import androidx.lifecycle.*
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllExercisesViewModel @Inject constructor(private val repository: ExerciseRepository) : ViewModel() {
    val allEx: LiveData<List<Exercise>> = repository.getExerciseList().asLiveData()
    val isAdded = MutableLiveData<Boolean>(false)

    private fun someFun(){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertExercise(
                Exercise(
                    "test_name",
                    "",
                    "some_description",
                    false,
                    0
                )
            )
            isAdded.postValue(true)
        }
    }

}