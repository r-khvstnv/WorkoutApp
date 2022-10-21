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
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.utils.Constants

@Entity(tableName = Constants.BMI_TABLE)
data class BmiEntity(
    @ColumnInfo val date: Long,
    @ColumnInfo val weight: Float,
    @ColumnInfo val height: Float,
    @ColumnInfo val bmiIndex: Float,

    @PrimaryKey(autoGenerate = true) var id: Int = 0
){
    companion object{
        fun fromBmi(bmiParam: BmiParam): BmiEntity {
            return BmiEntity(bmiParam.date, bmiParam.weight, bmiParam.height, bmiParam.bmiIndex, bmiParam.id)
        }
    }

    fun toBmi(): BmiParam {
        return BmiParam(date, weight, height, bmiIndex, id)
    }
}
