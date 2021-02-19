package com.rssll971.workoutapp

class BmiHistoryModelClass(private var _id: Int,
                           private var _date: String,
                           private var _weight: Float,
                           private var _height: Float,
                           private var _bmiIndex: Float) {
    fun getDate(): String{
        return _date
    }
    fun getWeight(): Float{
        return _weight
    }
    fun getHeight(): Float{
        return _height
    }
    fun getBmiIndex(): Float{
        return _bmiIndex
    }
}