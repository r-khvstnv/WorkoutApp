package com.rssll971.fitnessassistantapp.coredata.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rssll971.fitnessassistantapp.coredata.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.coredata.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.coredata.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.coredata.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.db.entity.StatisticEntity


@Database(entities = [
    BmiEntity::class,
    ExerciseEntity::class,
    StatisticEntity::class],
    version = 1
)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract fun getBmiDao(): BmiDao
    abstract fun getExerciseDao(): ExerciseDao
    abstract fun getStatisticDao(): StatisticDao
}