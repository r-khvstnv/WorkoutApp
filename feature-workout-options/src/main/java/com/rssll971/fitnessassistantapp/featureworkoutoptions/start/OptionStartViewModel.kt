package com.rssll971.fitnessassistantapp.featureworkoutoptions.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.rssll971.fitnessassistantapp.core.utils.CoreUtils
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class OptionStartViewModel @Inject constructor(private val repository: StatisticRepository) : ViewModel() {
    private var _isLineChartShouldBeShown = MutableLiveData(false)
    val isLineChartShouldBeShown: LiveData<Boolean> get() = _isLineChartShouldBeShown
    private var _workoutDurationEntries: MutableLiveData<List<Entry>> = MutableLiveData()
    private var _workoutExerciseAmountEntries: MutableLiveData<List<Entry>> = MutableLiveData()
    val workoutDurationEntries: LiveData<List<Entry>> get() = _workoutDurationEntries
    val workoutExerciseAmountEntries: LiveData<List<Entry>> get() = _workoutExerciseAmountEntries

    private var _chartAssociationDateList: MutableLiveData<List<Long>> = MutableLiveData()

    init {
        fetchData()
    }

    private fun fetchData(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getStatisticList().collect {
                list ->
                if (list.isEmpty()){
                    _isLineChartShouldBeShown.postValue(false)
                } else{
                    _isLineChartShouldBeShown.postValue(true)
                    val durationEntries = arrayListOf<Entry>()
                    val exerciseAmountEntries = arrayListOf<Entry>()
                    val associationList = arrayListOf<Long>()
                    var counter = 10

                    for (i in list.indices){
                        if (counter != 0){
                            associationList.add(list[i].date)
                            durationEntries.add(Entry(i.toFloat(), (list[i].restDuration + list[i].exerciseDuration) * list[i].selectedExerciseIds.size.toFloat()))
                            exerciseAmountEntries.add(Entry(i.toFloat(), list[i].selectedExerciseIds.size.toFloat()))

                            counter--
                        } else{
                            break
                        }
                    }

                    _chartAssociationDateList.postValue(associationList.toList())
                    _workoutDurationEntries.postValue(durationEntries.toList())
                    _workoutExerciseAmountEntries.postValue(exerciseAmountEntries.toList())
                }
            }
        }
    }

    fun getDateByIndex(index: Int): String{
        return _chartAssociationDateList.value?.let {
            CoreUtils.formatDateToDayMonth(it[index])
        } ?: ""
    }
}