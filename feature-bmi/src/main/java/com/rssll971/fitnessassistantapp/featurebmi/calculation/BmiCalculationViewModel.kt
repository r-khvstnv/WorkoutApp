package com.rssll971.fitnessassistantapp.featurebmi.calculation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rssll971.fitnessassistantapp.coredata.db.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.models.Bmi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow

class BmiCalculationViewModel @Inject constructor( private val repository: BmiRepository) : ViewModel() {
    private var _bmi: MutableLiveData<Bmi> = MutableLiveData()
    val bmi: LiveData<Bmi> get() = _bmi

    /**Method calculates BmiIndex and after requests to save it*/
    fun calculateBmi(dateInMillis: Long, height: Float, weight: Float){
        val tmpHeight: Float = height / 100f
        val index: Float = weight / (tmpHeight.pow(2))
        val bmi = Bmi(
            dateInMillis,
            weight = weight,
            height = height,
            index,
            0
        )
        _bmi.postValue(bmi)
        insertBmi(bmi = bmi)
    }

    /**Method inserts bmi to database*/
    private fun insertBmi(bmi: Bmi){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertBmi(bmi = bmi)
        }
    }
}