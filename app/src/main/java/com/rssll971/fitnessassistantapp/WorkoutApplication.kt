package com.rssll971.fitnessassistantapp

import android.app.Application
import com.rssll971.fitnessassistantapp.di.AppComponent
import com.rssll971.fitnessassistantapp.di.DaggerAppComponent
import com.rssll971.fitnessassistantapp.featurebmi.utils.FeatureBmiDepsStore
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExercisesDepsStore
import com.rssll971.fitnessassistantapp.featureworkout.workout.di.WorkoutDepsStore
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSDepsStore
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartDepsStore


class WorkoutApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.let {
            FeatureExercisesDepsStore.deps = it
            FeatureBmiDepsStore.deps = it
            OptionStartDepsStore.deps = it
            OptionsFSDepsStore.deps = it
            WorkoutDepsStore.deps = it
        }
    }

}