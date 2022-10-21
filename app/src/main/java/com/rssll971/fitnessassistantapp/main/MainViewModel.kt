/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.main

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rssll971.fitnessassistantapp.core.utils.ConstantsCore
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.AddExerciseUseCase
import com.rssll971.fitnessassistantapp.coredata.utils.DefaultExercises
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

internal class MainViewModel @Inject constructor(
    private val addExerciseUseCase: AddExerciseUseCase
): ViewModel() {

    /**Method adds defaultExercises to database
     * Based on system language, corresponding exercises are added.
     * After successful insertion, sharedPreference value IS_FIRS_APP_LAUNCH will be changed to false*/
    fun insertDefaultExercises(sharedPreferences: SharedPreferences){
        viewModelScope.launch(Dispatchers.IO){
            val defaultExerciseListParam: List<ExerciseParam> = if (Locale.getDefault().language == "ru"){
                DefaultExercises.getRuExerciseList()
            } else{
                DefaultExercises.getEngExerciseList()
            }

            for (exercise in defaultExerciseListParam){
                addExerciseUseCase.invoke(param = exercise)
            }

            withContext(Dispatchers.Main){
                sharedPreferences.edit {
                    putBoolean(ConstantsCore.IS_FIRS_APP_LAUNCH, false)
                    apply()
                }
            }
        }
    }
}