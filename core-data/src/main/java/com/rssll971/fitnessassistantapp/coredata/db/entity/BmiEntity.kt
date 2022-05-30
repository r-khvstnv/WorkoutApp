package com.rssll971.fitnessassistantapp.coredata.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.BMI_TABLE)
data class BmiEntity(
    @ColumnInfo val date: String,
    @ColumnInfo val weight: Float,
    @ColumnInfo val height: Float,
    @ColumnInfo val bmiIndex: Float,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
