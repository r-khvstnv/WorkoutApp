/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.rssll971.fitnessassistantapp.featurebmi.R
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

internal object Utils {
    /**
     * Method formats [dateInMillis] to day-month-year [String] representation.
     * */
    fun formatToDate(dateInMillis: Long): String{
        val date = Date(dateInMillis)
        val sdf = SimpleDateFormat("dd-MM-yy", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
    /**
     * Method rounds [x] to one decimal point.
     * */
    fun getRoundedFloat(x: Float): String{
        val df = DecimalFormat("#.#")
        return  df.format(x)
    }
    /**
     * Method provides localised [String] for corresponding bmi [index].
     * */
    fun getBmiIndexStatus(index: Float, context: Context): String{
        return when(index){
            in 0.0f..18.59f -> context.getString(R.string.index_underweight)
            in 18.6f..24.99f -> context.getString(R.string.index_normal)
            in 25.0f..29.99f -> context.getString(R.string.index_overweight)
            in 30.0f..34.99f -> context.getString(R.string.index_obese)
            else -> context.getString(R.string.index_ex_obese)
        }
    }
    /**
     * Method provides Color [Int] for corresponding bmi [index].
     * */
    fun getBmiIndexStatusColor(index: Float, context: Context): Int{
        return when(index){
            in 0.0f..18.59f -> ContextCompat.getColor(context, R.color.color_underweight)
            in 18.6f..24.99f -> ContextCompat.getColor(context, R.color.color_normal)
            in 25.0f..29.99f -> ContextCompat.getColor(context, R.color.color_overweight)
            in 30.0f..34.99f -> ContextCompat.getColor(context, R.color.color_obese)
            else -> ContextCompat.getColor(context, R.color.color_ex_obese)
        }
    }
}