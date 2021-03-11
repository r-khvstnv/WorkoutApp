package com.rssll971.fitnessassistantapp.modelclasses

class WorkoutStatisticModelClass(private val _id: Int,
                                 private val _date: String,
                                 private val _restDuration: Int,
                                 private val _exerciseDuration: Int,
                                 private val _exerciseAmount: Int) {
    fun getId(): Int{
        return _id
    }
    fun getDate(): String{
        return _date
    }
    fun getRestDuration(): Int{
        return _restDuration
    }
    fun getExerciseDuration(): Int{
        return _exerciseDuration
    }
    fun getExerciseAmount(): Int{
        return _exerciseAmount
    }

}