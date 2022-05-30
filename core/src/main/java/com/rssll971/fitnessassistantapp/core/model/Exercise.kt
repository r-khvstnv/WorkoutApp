package com.rssll971.fitnessassistantapp.core.model

data class Exercise(
    val name: String,
    val imagePath: String = "",
    val description: String,
    var isFinished: Boolean = false,
    val id: Int = 0
)
