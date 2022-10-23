/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import com.rssll971.fitnessassistantapp.coredata.utils.IntTypeConverter

@Entity(tableName = Constants.STATISTIC_TABLE)
internal data class StatisticEntity(
    @ColumnInfo val date: Long,
    @ColumnInfo val restDuration: Int,
    @ColumnInfo val exerciseDuration: Int,
    @ColumnInfo val isVoiceEnable: Boolean,
    @field:TypeConverters(IntTypeConverter::class) val selectedExerciseIds: List<Int>,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
