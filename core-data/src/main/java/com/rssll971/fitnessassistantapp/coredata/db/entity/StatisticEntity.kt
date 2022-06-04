package com.rssll971.fitnessassistantapp.coredata.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rssll971.fitnessassistantapp.coredata.models.Statistic
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import com.rssll971.fitnessassistantapp.coredata.utils.IntTypeConverter

@Entity(tableName = Constants.STATISTIC_TABLE)
data class StatisticEntity(
    @ColumnInfo val date: Long,
    @ColumnInfo val restDuration: Int,
    @ColumnInfo val exerciseDuration: Int,
    @ColumnInfo val exerciseAmount: Int,
    @ColumnInfo val isVoiceEnable: Boolean,
    @field:TypeConverters(IntTypeConverter::class) val selectedExerciseIds: List<Int>,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    companion object{
        fun fromStatistic(statistic: Statistic): StatisticEntity{
            return StatisticEntity(
                statistic.date,
                statistic.restDuration,
                statistic.exerciseDuration,
                statistic.exerciseAmount,
                statistic.isVoiceEnable,
                statistic.selectedExerciseIds,
                statistic.id
            )
        }
    }

    fun toStatistic(): Statistic{
        return Statistic(date, restDuration, exerciseDuration, exerciseAmount, isVoiceEnable, selectedExerciseIds, id)
    }
}
