package com.rssll971.fitnessassistantapp.featureexercise.all

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AllViewModel @Inject constructor() : ViewModel() {
    val testText = MutableLiveData<String>("View Model Works")
}