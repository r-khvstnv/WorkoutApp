package com.rssll971.fitnessassistantapp.featureexercise.utils

import com.rssll971.fitnessassistantapp.coredata.models.Exercise

interface ItemCallback {
    fun onClick(id: Int)
    fun onDelete(exercise: Exercise)
}