/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di.app

import android.app.Application
import com.rssll971.fitnessassistantapp.coredata.db.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.featurebmi.di.FeatureBmiDeps
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExercisesDeps
import com.rssll971.fitnessassistantapp.featureworkout.workout.di.WorkoutDeps
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSDeps
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartDeps
import com.rssll971.fitnessassistantapp.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent :
    FeatureExercisesDeps, //feature-exercise
    FeatureBmiDeps, //feature-bmi
    OptionStartDeps, OptionsFSDeps, //feature-workout-options
    WorkoutDeps //feature-workout
{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override val repositoryExercise: ExerciseRepository
    override val repositoryBmi: BmiRepository
    override val repositoryStatistic: StatisticRepository


}