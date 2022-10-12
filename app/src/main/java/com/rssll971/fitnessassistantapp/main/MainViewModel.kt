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
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.coredata.utils.DefaultExercises
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

internal class MainViewModel @Inject constructor(private val repository: ExerciseRepository): ViewModel() {

    /**Method adds defaultExercises to database
     * Based on system language, corresponding exercises are added.
     * After successful insertion, sharedPreference value IS_FIRS_APP_LAUNCH will be changed to false*/
    fun insertDefaultExercises(sharedPreferences: SharedPreferences){
        viewModelScope.launch(Dispatchers.IO){
            val defaultExerciseList: List<Exercise> = if (Locale.getDefault().language == "ru"){
                DefaultExercises.getRuExerciseList()
            } else{
                DefaultExercises.getEngExerciseList()
            }

            for (exercise in defaultExerciseList){
                repository.insertExercise(exercise = exercise)
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