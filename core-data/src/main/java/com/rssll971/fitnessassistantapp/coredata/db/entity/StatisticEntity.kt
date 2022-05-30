package com.rssll971.fitnessassistantapp.coredata.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.STATISTIC_TABLE)
data class StatisticEntity(
    @ColumnInfo val date: String,
    @ColumnInfo val restDuration: Int,
    @ColumnInfo val exerciseDuration: Int,
    @ColumnInfo val exerciseAmount: Int,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
