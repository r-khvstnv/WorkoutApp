package com.rssll971.fitnessassistantapp.coredata.models

data class Statistic(
    val date: Long,
    val restDuration: Int,
    val exerciseDuration: Int,
    val isVoiceEnable: Boolean,
    val selectedExerciseIds: List<Int>,
    val id: Int
)