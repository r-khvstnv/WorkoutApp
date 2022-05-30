package com.rssll971.fitnessassistantapp.coredata.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Statistic(
    val date: String,
    val restDuration: Int,
    val exerciseDuration: Int,
    val exerciseAmount: Int,
    val id: Int
)