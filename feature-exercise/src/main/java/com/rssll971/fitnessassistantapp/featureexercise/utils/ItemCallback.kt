package com.rssll971.fitnessassistantapp.featureexercise.utils

import com.rssll971.fitnessassistantapp.coredata.models.Exercise

internal interface ItemCallback {
    fun onClick(id: Int)
    fun onDelete(exercise: Exercise)
}