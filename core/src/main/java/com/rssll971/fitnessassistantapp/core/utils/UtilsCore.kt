/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.core.utils

import java.text.SimpleDateFormat
import java.util.*

object UtilsCore {
    /**
     * Method formats [timeL] to minutes:seconds [String] representation.
     *
     * [timeL] must be in milliseconds.
     * */
    fun getFormattedTime(timeL: Long): String{
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())
        return sdf.format(timeL)
    }

    /**
     * Method formats [timeL] to Day.Month [String] representation.
     * @sample [output = 22.10]
     *
     * [timeL] must be in milliseconds.
     * */
    fun formatDateToDayMonth(timeL: Long): String{
        val date = Date(timeL)
        val sdf = SimpleDateFormat("dd.MM", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}