/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkout.workout.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import kotlin.properties.Delegates


/**
 * Interface for implicit dependency on AppComponent
 * (due to feature-module doesn't know anything about app-module).
 * As declared properties listed all methods required for further injection
 * in corresponding feature-module.
 *
 * Note: Should be inherited in AppComponent
 * */
interface WorkoutDeps {
    val repositoryStatistic: StatisticRepository
    val repositoryExercise: ExerciseRepository
}
/**
 * Interface provides required instances from appComponent
 * */
interface WorkoutDepsProvider{
    //Restrict getter to current module
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: WorkoutDeps

    //Real existed instance will be provided from DepsStore
    companion object: WorkoutDepsProvider by WorkoutDepsStore
}
/**
 * Explicit Singleton which must be assigned in WorkoutApplication
 * */
object WorkoutDepsStore: WorkoutDepsProvider{
    override var deps: WorkoutDeps by Delegates.notNull()
}
/**
 * ViewModel stores all instances of Components.
 * This implementation simplifies Component lifecycle handling.
 * Note: We can't store instances in original (related to Fragment) ViewModel,
 * due to it creates by ViewModelFactory which should be injected too.
 * */
internal class WorkoutComponentViewModel: ViewModel(){
    val workoutComponent = DaggerWorkoutComponent.builder().deps(WorkoutDepsProvider.deps).build()
}