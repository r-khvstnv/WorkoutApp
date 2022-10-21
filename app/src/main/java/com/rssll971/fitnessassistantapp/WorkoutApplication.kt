/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp

import android.app.Application
import com.rssll971.fitnessassistantapp.di.app.AppComponent
import com.rssll971.fitnessassistantapp.di.app.DaggerAppComponent
import com.rssll971.fitnessassistantapp.featurebmi.di.FeatureBmiDepsStore
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExercisesDepsStore
import com.rssll971.fitnessassistantapp.featureworkout.workout.di.WorkoutDepsStore
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSDepsStore
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartDepsStore

class WorkoutApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        /**Pass an instance of appComponent to corresponding Feature Dependency Stores*/
        appComponent.let {
            FeatureExercisesDepsStore.deps = it
            FeatureBmiDepsStore.deps = it
            OptionStartDepsStore.deps = it
            OptionsFSDepsStore.deps = it
            WorkoutDepsStore.deps = it
        }
    }

}