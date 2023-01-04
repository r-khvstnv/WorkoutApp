/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.db.dao

import androidx.room.*
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
internal interface BmiDao {
    @Insert
    suspend fun insertBmiEntity(bmiEntity: BmiEntity)

    @Query("DELETE FROM ${Constants.BMI_TABLE}")
    suspend fun deleteAllEntities()

    @Query("SELECT * FROM ${Constants.BMI_TABLE} ORDER BY ${Constants.ID}")
    fun getAllBmiEntities(): Flow<List<BmiEntity>>
}