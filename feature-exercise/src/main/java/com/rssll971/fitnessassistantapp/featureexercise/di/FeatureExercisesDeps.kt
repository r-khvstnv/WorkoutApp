/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.*
import com.rssll971.fitnessassistantapp.featureexercise.addedit.di.DaggerAddEditComponent
import com.rssll971.fitnessassistantapp.featureexercise.all.di.DaggerAllExercisesComponent
import kotlin.properties.Delegates
/**
 * NOTE: This approach is implemented within the Module Scope, since its separate parts
 * request the same dependencies. However, method can be used for each component separately.
 * */

/**
 * Interface for implicit dependency on AppComponent
 * (due to feature-module doesn't know anything about app-module).
 * As declared properties listed all methods required for further injection
 * in corresponding feature-module.
 *
 * Note: Should be inherited in AppComponent
 * */
interface FeatureExercisesDeps{
    val addExerciseUseCase: AddExerciseUseCase
    val deleteExerciseUseCase: DeleteExerciseUseCase
    val getAllExercisesUseCase: GetAllExercisesUseCase
    val getExerciseByIdUseCase: GetExerciseByIdUseCase
    val updateExerciseUseCase: UpdateExerciseUseCase
}

/**
 * Interface provides required instances from appComponent
 * */
interface FeatureExercisesDepsProvider{
    //Restrict getter to current module
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: FeatureExercisesDeps

    //Real existed instance will be provided from DepsStore
    companion object : FeatureExercisesDepsProvider by FeatureExercisesDepsStore

}

/**
 * Explicit Singleton which must be assigned in WorkoutApplication
 * */
object FeatureExercisesDepsStore: FeatureExercisesDepsProvider {
    override var deps: FeatureExercisesDeps by Delegates.notNull()
}


/**
 * ViewModel stores all instances of Components.
 * This implementation simplifies Component lifecycle handling.
 * Note: We can't store instances in original (related to Fragment) ViewModel,
 * due to it creates by ViewModelFactory which should be injected too.
 * */
internal class FeatureExerciseComponentsViewModel : ViewModel(){
    val allExercisesComponent = DaggerAllExercisesComponent.builder().deps(
        FeatureExercisesDepsProvider.deps).build()
    val addEditExerciseComponent = DaggerAddEditComponent.builder().deps(
        FeatureExercisesDepsProvider.deps).build()
}