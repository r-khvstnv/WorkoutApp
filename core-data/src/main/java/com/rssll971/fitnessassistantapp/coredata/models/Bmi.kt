package com.rssll971.fitnessassistantapp.coredata.models

data class Bmi(
    val date: String,
    val weight: Float,
    val height: Float,
    val bmiIndex: Float,
    val id: Int
)