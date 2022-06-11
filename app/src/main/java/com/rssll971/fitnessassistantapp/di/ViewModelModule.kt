/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.di

import androidx.lifecycle.ViewModel
import com.rssll971.fitnessassistantapp.core.di.annotation.ViewModelKey
import com.rssll971.fitnessassistantapp.featurebmi.calculation.BmiCalculationViewModel
import com.rssll971.fitnessassistantapp.featurebmi.history.BmiHistoryViewModel
import com.rssll971.fitnessassistantapp.featureexercise.addedit.AddEditExerciseViewModel
import com.rssll971.fitnessassistantapp.featureexercise.all.AllExercisesViewModel
import com.rssll971.fitnessassistantapp.featureworkout.workout.WorkoutViewModel
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.OptionsViewModel
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.OptionStartViewModel
import com.rssll971.fitnessassistantapp.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {
    @Binds
    @[IntoMap ViewModelKey(MainViewModel::class)]
    abstract fun bindsMainViewModel(mainViewModel: MainViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(AddEditExerciseViewModel::class)]
    abstract fun bindsAddEditExerciseViewModel(addEditExerciseViewModel: AddEditExerciseViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(AllExercisesViewModel::class)]
    abstract fun bindsAllExercisesViewModel(allExercisesViewModel: AllExercisesViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(BmiCalculationViewModel::class)]
    abstract fun bindsBmiCalculationViewModel(bmiCalculationViewModel: BmiCalculationViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(BmiHistoryViewModel::class)]
    abstract fun bindsBmiHistoryViewModel(bmiHistoryViewModel: BmiHistoryViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(OptionStartViewModel::class)]
    abstract fun bindsOptionStartViewModel(optionStartViewModel: OptionStartViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(OptionsViewModel::class)]
    abstract fun bindsOptionsViewModel(optionsViewModel: OptionsViewModel): ViewModel
    @Binds
    @[IntoMap ViewModelKey(WorkoutViewModel::class)]
    abstract fun bindsWorkoutViewModel(workoutViewModel: WorkoutViewModel): ViewModel
}