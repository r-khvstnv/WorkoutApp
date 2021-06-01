package com.rssll971.fitnessassistantapp

import com.rssll971.fitnessassistantapp.models.ExerciseModel

interface ExerciseAdapterCallback {
    fun onItemSelected(position: Int, isNeededAdd: Boolean)
    fun onItemEditing(exerciseModel: ExerciseModel, isExisted: Boolean)
}