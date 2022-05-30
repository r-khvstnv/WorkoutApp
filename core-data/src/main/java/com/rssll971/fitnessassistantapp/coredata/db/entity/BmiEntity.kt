package com.rssll971.fitnessassistantapp.coredata.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rssll971.fitnessassistantapp.coredata.models.Bmi
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.BMI_TABLE)
data class BmiEntity(
    @ColumnInfo val date: String,
    @ColumnInfo val weight: Float,
    @ColumnInfo val height: Float,
    @ColumnInfo val bmiIndex: Float,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    companion object{
        fun fromBmi(bmi: Bmi): BmiEntity{
            return BmiEntity(bmi.date, bmi.weight, bmi.height, bmi.bmiIndex)
        }
    }

    fun toBmi(): Bmi{
        return Bmi(date, weight, height, bmiIndex, id)
    }
}
