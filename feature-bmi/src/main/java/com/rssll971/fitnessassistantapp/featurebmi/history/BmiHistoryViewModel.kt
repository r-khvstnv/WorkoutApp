package com.rssll971.fitnessassistantapp.featurebmi.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rssll971.fitnessassistantapp.coredata.db.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.models.Bmi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class BmiHistoryViewModel @Inject constructor(private val repository: BmiRepository): ViewModel() {
    val bmiList: LiveData<List<Bmi>> = repository.getBmiList().asLiveData()

    fun deleteAllBmi(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllBmi()
        }
    }

}