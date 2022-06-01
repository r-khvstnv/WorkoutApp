package com.rssll971.fitnessassistantapp.featurebmi.calculation

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.coredata.db.repository.BmiRepository
import javax.inject.Inject

class BmiCalculationViewModel @Inject constructor( private val repository: BmiRepository) : ViewModel() {

}