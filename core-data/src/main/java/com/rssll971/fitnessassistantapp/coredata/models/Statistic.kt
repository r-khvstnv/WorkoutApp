package com.rssll971.fitnessassistantapp.coredata.models

data class Statistic(
    val date: String,
    val restDuration: Int,
    val exerciseDuration: Int,
    val exerciseAmount: Int,
    val id: Int
)