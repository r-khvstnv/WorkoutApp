package com.rssll971.fitnessassistantapp

import android.app.Application
import com.rssll971.fitnessassistantapp.di.AppComponent
import com.rssll971.fitnessassistantapp.di.DaggerAppComponent
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExercisesDepsStore


class WorkoutApplication: Application() {
    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()

        FeatureExercisesDepsStore.deps = appComponent
    }

}