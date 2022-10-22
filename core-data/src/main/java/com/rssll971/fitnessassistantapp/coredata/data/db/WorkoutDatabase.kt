/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.coredata.data.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.data.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.utils.IntTypeConverter


@Database(entities = [
    BmiEntity::class,
    ExerciseEntity::class,
    StatisticEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntTypeConverter::class)
internal abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun getBmiDao(): BmiDao
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getStatisticDao(): StatisticDao
}