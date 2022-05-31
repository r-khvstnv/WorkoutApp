package com.rssll971.fitnessassistantapp.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExercisesDeps
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent : FeatureExercisesDeps {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override val repositoryExercise: ExerciseRepository
    override val viewModelFactory: ViewModelProvider.Factory
}