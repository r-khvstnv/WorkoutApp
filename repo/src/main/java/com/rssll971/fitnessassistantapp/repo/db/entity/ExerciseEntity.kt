package com.rssll971.fitnessassistantapp.repo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rssll971.fitnessassistantapp.repo.utils.Constants

@Entity(tableName = Constants.EXERCISE_TABLE)
data class ExerciseEntity(
    @ColumnInfo val name: String,
    @ColumnInfo val imagePath: String,
    @ColumnInfo val description: String,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
)
