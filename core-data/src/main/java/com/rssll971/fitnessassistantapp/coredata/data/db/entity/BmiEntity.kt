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
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.BMI_TABLE)
internal data class BmiEntity(
    @ColumnInfo val date: Long,
    @ColumnInfo val weight: Float,
    @ColumnInfo val height: Float,
    @ColumnInfo val bmiIndex: Float,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
