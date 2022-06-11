/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.models

data class Bmi(
    val date: Long,
    val weight: Float,
    val height: Float,
    val bmiIndex: Float,
    val id: Int
)