package com.rssll971.fitnessassistantapp.models

data class WorkoutStatisticModel(val id: Int,
                                 val date: String,
                                 val restDuration: Int,
                                 val exerciseDuration: Int,
                                 val exerciseAmount: Int)