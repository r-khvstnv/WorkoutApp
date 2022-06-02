package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import javax.inject.Inject

class OptionsViewModel @Inject constructor(
    private val repoStatistic: StatisticRepository,
    private val repoExercise: ExerciseRepository
    ): ViewModel() {
}