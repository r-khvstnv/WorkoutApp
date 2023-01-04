/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.model

data class BmiParam(
    val date: Long,
    val weight: Float,
    val height: Float,
    val bmiIndex: Float,
    val id: Int
)