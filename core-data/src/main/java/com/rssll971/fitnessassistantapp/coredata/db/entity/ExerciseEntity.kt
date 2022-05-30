package com.rssll971.fitnessassistantapp.coredata.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.EXERCISE_TABLE)
data class ExerciseEntity(
    @ColumnInfo val name: String,
    @ColumnInfo val imagePath: String,
    @ColumnInfo val description: String,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    companion object{
        fun fromExercise(exercise: Exercise): ExerciseEntity{
            return ExerciseEntity(exercise.name, exercise.imagePath, exercise.description)
        }
    }

    fun toExercise(): Exercise{
        return Exercise(name, imagePath, description, false, id)
    }
}
