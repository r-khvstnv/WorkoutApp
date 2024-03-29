/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


internal class IntTypeConverter {
    @TypeConverter
    fun saveIntList(list: List<Int>): String{
        return Gson().toJson(list)
    }
    @TypeConverter
    fun getIntList(list: String): List<Int>{
        return Gson().fromJson(list, object : TypeToken<List<Int>>(){}.type)
    }
}