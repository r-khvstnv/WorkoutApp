/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.history

import androidx.lifecycle.*
import com.github.mikephil.charting.data.BarEntry
import com.rssll971.fitnessassistantapp.core.utils.UtilsCore
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.DeleteAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.GetAllBmiUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class BmiHistoryViewModel @Inject constructor(
    private val getAllBmiUseCase: GetAllBmiUseCase,
    private val deleteAllBmiUseCase: DeleteAllBmiUseCase,
): ViewModel() {
    private var _bmiParamList: MutableLiveData<List<BmiParam>> = MutableLiveData()
    val bmiParamList: LiveData<List<BmiParam>> get() = _bmiParamList

    private var _bmiBarEntries: MutableLiveData<List<BarEntry>> = MutableLiveData()
    val bmiBarEntries: LiveData<List<BarEntry>> get() = _bmiBarEntries
    private var _chartAssociationDateList: MutableLiveData<List<Long>> = MutableLiveData()


    init {
        fetchData()
    }

    /**Method deletes all bmiEntities from database4*/
    fun deleteAllBmi(){
        viewModelScope.launch(Dispatchers.IO){
            deleteAllBmiUseCase.invoke()
        }
    }

    /**Method fetch data from Database and collect it to corresponding entryList and associatedList*/
    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO){
            getAllBmiUseCase.invoke().collect {
                list ->
                _bmiParamList.postValue(list)

                if (list.isNotEmpty()){
                    val barEntryList = arrayListOf<BarEntry>()
                    val associationList = arrayListOf<Long>()
                    for (i in list.indices){
                        barEntryList.add(BarEntry(i.toFloat(), list[i].bmiIndex))
                        associationList.add(list[i].date)
                    }

                    _chartAssociationDateList.postValue(associationList.toList())
                    _bmiBarEntries.postValue(barEntryList.toList())
                }
            }
        }
    }


    /**Method return associated date for requested list index*/
    fun getDateByIndex(index: Int): String{
        return _chartAssociationDateList.value?.let {
            UtilsCore.formatDateToDayMonth(it[index])
        } ?: ""
    }
}