package com.rssll971.fitnessassistantapp.repo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rssll971.fitnessassistantapp.repo.db.dao.BmiDao
import com.rssll971.fitnessassistantapp.repo.db.dao.ExerciseDao
import com.rssll971.fitnessassistantapp.repo.db.dao.StatisticDao
import com.rssll971.fitnessassistantapp.repo.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.repo.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.repo.db.entity.StatisticEntity


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