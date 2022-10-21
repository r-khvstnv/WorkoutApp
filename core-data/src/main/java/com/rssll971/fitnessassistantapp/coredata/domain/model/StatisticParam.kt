/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.model

data class StatisticParam(
    val date: Long,
    val restDuration: Int,
    val exerciseDuration: Int,
    val isVoiceEnable: Boolean,
    val selectedExerciseIds: List<Int>,
    val id: Int
)