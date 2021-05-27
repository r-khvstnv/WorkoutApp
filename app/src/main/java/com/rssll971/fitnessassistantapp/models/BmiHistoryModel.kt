package com.rssll971.fitnessassistantapp.models

data class BmiHistoryModel(val id: Int,
                           val date: String,
                           val weight: Float,
                           val height: Float,
                           val bmiIndex: Float)