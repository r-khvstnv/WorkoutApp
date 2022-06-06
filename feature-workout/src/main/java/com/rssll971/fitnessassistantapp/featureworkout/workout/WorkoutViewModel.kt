package com.rssll971.fitnessassistantapp.featureworkout.workout

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import javax.inject.Inject

class WorkoutViewModel @Inject constructor(
    private val repoStatistic: StatisticRepository,
    private val repoExercise: ExerciseRepository
) : ViewModel() {
}