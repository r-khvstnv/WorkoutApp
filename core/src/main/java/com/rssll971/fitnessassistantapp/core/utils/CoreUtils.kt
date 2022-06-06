package com.rssll971.fitnessassistantapp.core.utils

import java.text.SimpleDateFormat
import java.util.*

object CoreUtils {
    fun getFormattedTime(timeL: Long): String{
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())
        return sdf.format(timeL)
    }
}