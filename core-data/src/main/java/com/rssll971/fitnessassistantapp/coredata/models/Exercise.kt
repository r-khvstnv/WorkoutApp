package com.rssll971.fitnessassistantapp.coredata.models

data class Exercise(
    val name: String,
    val imagePath: String = "",
    val description: String,
    val id: Int,
    var isFinished: Boolean = false,
    var isSelected: Boolean = false
)
