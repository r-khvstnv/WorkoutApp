package com.rssll971.fitnessassistantapp.coredata.models

data class Exercise(
    val name: String,
    val imagePath: String = "",
    val description: String,
    var isFinished: Boolean = false,
    val id: Int
)
