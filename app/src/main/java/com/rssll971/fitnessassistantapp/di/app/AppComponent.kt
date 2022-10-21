/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di.app

import android.app.Application
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.AddBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.DeleteAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.GetAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.*
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetAllStatisticUseCase
import com.rssll971.fitnessassistantapp.featurebmi.di.FeatureBmiDeps
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExercisesDeps
import com.rssll971.fitnessassistantapp.featureworkout.workout.di.WorkoutDeps
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSDeps
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent :
    FeatureExercisesDeps, //feature-exercise
    FeatureBmiDeps, //feature-bmiParam
    OptionStartDeps, OptionsFSDeps, //feature-workout-options
    WorkoutDeps //feature-workout
{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override val addBmiUseCase: AddBmiUseCase
    override val deleteAllBmiUseCase: DeleteAllBmiUseCase
    override val getAllBmiUseCase: GetAllBmiUseCase

    override val addExerciseUseCase: AddExerciseUseCase
    override val deleteExerciseUseCase: DeleteExerciseUseCase
    override val getAllExercisesUseCase: GetAllExercisesUseCase
    override val getExerciseByIdUseCase: GetExerciseByIdUseCase
    override val updateExerciseUseCase: UpdateExerciseUseCase
    override val getExercisesByIdListUseCase: GetExercisesByIdListUseCase

    override val addStatisticUseCase: AddStatisticUseCase
    override val getAllStatisticUseCase: GetAllStatisticUseCase

}