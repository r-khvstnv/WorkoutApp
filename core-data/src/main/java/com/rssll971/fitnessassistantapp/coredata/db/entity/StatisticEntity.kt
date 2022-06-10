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
    @ColumnInfo val isVoiceEnable: Boolean,
    /**Variable allows to transfer data between fragments, using database.
     * In the future it will help track the statistics of the user's favorite exercises*/
    @field:TypeConverters(IntTypeConverter::class) val selectedExerciseIds: List<Int>,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    companion object{
        fun fromStatistic(statistic: Statistic): StatisticEntity{
            return StatisticEntity(
                statistic.date,
                statistic.restDuration,
                statistic.exerciseDuration,
                statistic.isVoiceEnable,
                statistic.selectedExerciseIds,
                statistic.id
            )
        }
    }

    fun toStatistic(): Statistic{
        return Statistic(date, restDuration, exerciseDuration, isVoiceEnable, selectedExerciseIds, id)
    }
}
