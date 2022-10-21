/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.calculation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.AddBmiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow

internal class BmiCalculationViewModel @Inject constructor(
    private val addBmiUseCase: AddBmiUseCase
) : ViewModel() {
    private var _bmiParam: MutableLiveData<BmiParam> = MutableLiveData()
    val bmiParam: LiveData<BmiParam> get() = _bmiParam

    /**Method calculates BmiIndex and after requests to save it*/
    fun calculateBmi(dateInMillis: Long, height: Float, weight: Float){
        val tmpHeight: Float = height / 100f
        val index: Float = weight / (tmpHeight.pow(2))
        val bmiParam = BmiParam(
            dateInMillis,
            weight = weight,
            height = height,
            index,
            0
        )
        _bmiParam.postValue(bmiParam)
        insertBmi(bmiParam = bmiParam)
    }

    /**Method inserts bmiParam to database*/
    private fun insertBmi(bmiParam: BmiParam){
        viewModelScope.launch(Dispatchers.IO){
            addBmiUseCase.invoke(param = bmiParam)
        }
    }
}