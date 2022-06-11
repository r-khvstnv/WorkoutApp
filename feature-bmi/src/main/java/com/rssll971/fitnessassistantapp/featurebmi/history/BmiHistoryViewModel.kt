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
import com.rssll971.fitnessassistantapp.coredata.db.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.models.Bmi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class BmiHistoryViewModel @Inject constructor(private val repository: BmiRepository): ViewModel() {
    private var _bmiList: MutableLiveData<List<Bmi>> = MutableLiveData()
    val bmiList: LiveData<List<Bmi>> get() = _bmiList

    private var _bmiBarEntries: MutableLiveData<List<BarEntry>> = MutableLiveData()
    val bmiBarEntries: LiveData<List<BarEntry>> get() = _bmiBarEntries
    private var _chartAssociationDateList: MutableLiveData<List<Long>> = MutableLiveData()


    init {
        fetchData()
    }

    /**Method deletes all bmiEntities from database4*/
    fun deleteAllBmi(){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllBmi()
        }
    }

    /**Method fetch data from Database and collect it to corresponding entryList and associatedList*/
    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getBmiList().collect {
                list ->
                _bmiList.postValue(list)

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