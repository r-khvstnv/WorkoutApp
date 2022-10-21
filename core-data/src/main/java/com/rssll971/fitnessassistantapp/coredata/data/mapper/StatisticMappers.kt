/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.mapper

import com.rssll971.fitnessassistantapp.coredata.data.db.entity.StatisticEntity
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.utils.Mapper

internal class StatisticEntityToParamMapper: Mapper<StatisticEntity, StatisticParam>{
    override fun map(input: StatisticEntity): StatisticParam = with(input){
        StatisticParam(
            date = date,
            restDuration = restDuration,
            exerciseDuration = exerciseDuration,
            isVoiceEnable = isVoiceEnable,
            selectedExerciseIds = selectedExerciseIds,
            id = id
        )
    }
}

internal class StatisticParamToEntityMapper: Mapper<StatisticParam, StatisticEntity>{
    override fun map(input: StatisticParam): StatisticEntity = with(input){
        StatisticEntity(
            date = date,
            restDuration = restDuration,
            exerciseDuration = exerciseDuration,
            isVoiceEnable = isVoiceEnable,
            selectedExerciseIds = selectedExerciseIds,
            id = id
        )
    }

}