package com.rssll971.fitnessassistantapp.coredata.models

data class Bmi(
    val date: Long,
    val weight: Float,
    val height: Float,
    val bmiIndex: Float,
    val id: Int
)