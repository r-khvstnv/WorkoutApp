/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartDepsProvider
import kotlin.properties.Delegates


/**
 * Interface for implicit dependency on AppComponent
 * (due to feature-module doesn't know anything about app-module).
 * As declared properties listed all methods required for further injection
 * in corresponding feature-module.
 *
 * Note: Should be inherited in AppComponent
 * */
interface OptionsFSDeps {
    val repositoryStatistic: StatisticRepository
    val repositoryExercise: ExerciseRepository
    val viewModelFactory: ViewModelProvider.Factory
}
/**
 * Interface provides required instances from appComponent
 * */
interface OptionsFSDepsProvider{
    //Restrict getter to current module
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: OptionsFSDeps

    //Real existed instance will be provided from DepsStore
    companion object: OptionsFSDepsProvider by OptionsFSDepsStore
}
/**
 * Explicit Singleton which must be assigned in WorkoutApplication
 * */
object OptionsFSDepsStore: OptionsFSDepsProvider{
    override var deps: OptionsFSDeps by Delegates.notNull()
}
/**
 * ViewModel stores all instances of Components.
 * This implementation simplifies Component lifecycle handling.
 * Note: We can't store instances in original (related to Fragment) ViewModel,
 * due to it creates by ViewModelFactory which should be injected too.
 * */
internal class OptionsFSComponentViewModel: ViewModel(){
    val optionsFSComponent = DaggerOptionsFSComponent.builder().deps(OptionsFSDepsProvider.deps).build()
}