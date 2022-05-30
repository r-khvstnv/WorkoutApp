package com.rssll971.fitnessassistantapp


import android.app.Application
import com.rssll971.fitnessassistantapp.di.AppComponent
import com.rssll971.fitnessassistantapp.di.DaggerAppComponent
import com.rssll971.fitnessassistantapp.featureexercise.all.di.FeatureExerciseDepsStore

class WorkoutApplication: Application() {
    lateinit var appComponent: AppComponent private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this).build()
        appComponent.inject(this)
        FeatureExerciseDepsStore.deps = appComponent
    }

}