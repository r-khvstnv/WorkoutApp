package com.rssll971.fitnessassistantapp.core.utils

import java.text.SimpleDateFormat
import java.util.*

object UtilsCore {
    fun getFormattedTime(timeL: Long): String{
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())
        return sdf.format(timeL)
    }

    fun formatDateToDayMonth(timeL: Long): String{
        val date = Date(timeL)
        val sdf = SimpleDateFormat("dd.MM", Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}