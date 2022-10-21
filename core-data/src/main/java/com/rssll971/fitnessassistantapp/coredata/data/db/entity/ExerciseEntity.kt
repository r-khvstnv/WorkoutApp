/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

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
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.EXERCISE_TABLE)
data class ExerciseEntity(
    @ColumnInfo val name: String,
    @ColumnInfo val imagePath: String,
    @ColumnInfo val description: String,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    companion object{
        fun fromExercise(exerciseParam: ExerciseParam): ExerciseEntity {
            return ExerciseEntity(exerciseParam.name, exerciseParam.imagePath, exerciseParam.description, exerciseParam.id)
        }
    }

    fun toExercise(): ExerciseParam {
        return ExerciseParam(name, imagePath, description, id)
    }
}
