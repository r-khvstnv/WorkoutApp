package com.rssll971.fitnessassistantapp.di

import android.app.Activity
import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.MainActivity
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.featureexercise.all.di.FeatureExerciseDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : FeatureExerciseDeps {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: Application)
    fun inject(activity: MainActivity)

    override val repositoryExercise: ExerciseRepository
    override val viewModelFactory: ViewModelProvider.Factory
}