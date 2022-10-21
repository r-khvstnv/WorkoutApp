/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.rssll971.fitnessassistantapp.core.utils.UtilsCore
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetAllStatisticUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class OptionStartViewModel @Inject constructor(
    private val getAllStatisticUseCase: GetAllStatisticUseCase,
) : ViewModel() {
    //Handles visibility state of Charts
    private var _isLineChartShouldBeShown = MutableLiveData(false)
    val isLineChartShouldBeShown: LiveData<Boolean> get() = _isLineChartShouldBeShown
    //LineChart Entries
    private var _workoutDurationEntries: MutableLiveData<List<Entry>> = MutableLiveData()
    private var _workoutExerciseAmountEntries: MutableLiveData<List<Entry>> = MutableLiveData()
    val workoutDurationEntries: LiveData<List<Entry>> get() = _workoutDurationEntries
    val workoutExerciseAmountEntries: LiveData<List<Entry>> get() = _workoutExerciseAmountEntries
    //List with associated datesL
    private var _chartAssociationDateList: MutableLiveData<List<Long>> = MutableLiveData()

    init {
        fetchData()
    }

    /**Method fetch data from Database and collect it to corresponding entryLists and associatedList*/
    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO){
            getAllStatisticUseCase.invoke().collect {
                list ->
                //If empty -> Only change charts visibility state to false
                if (list.isEmpty()){
                    _isLineChartShouldBeShown.postValue(false)
                }
                //If notEmpty -> change charts visibility state to true and assign data
                else{
                    _isLineChartShouldBeShown.postValue(true)
                    val durationEntries = arrayListOf<Entry>()
                    val exerciseAmountEntries = arrayListOf<Entry>()
                    val associationList = arrayListOf<Long>()

                    /**In Charts pass only last 10 entries*/
                    val tmpList: List<StatisticParam> = list.takeLast(10).reversed()
                    for (i in tmpList.indices){
                        associationList.add(list[i].date)
                        durationEntries.add(Entry(i.toFloat(), (list[i].restDuration + list[i].exerciseDuration) * list[i].selectedExerciseIds.size.toFloat()))
                        exerciseAmountEntries.add(Entry(i.toFloat(), list[i].selectedExerciseIds.size.toFloat()))
                    }

                    _chartAssociationDateList.postValue(associationList.toList())
                    _workoutDurationEntries.postValue(durationEntries.toList())
                    _workoutExerciseAmountEntries.postValue(exerciseAmountEntries.toList())
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