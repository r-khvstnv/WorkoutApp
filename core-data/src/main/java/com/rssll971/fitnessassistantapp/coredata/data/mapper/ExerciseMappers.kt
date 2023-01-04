/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.mapper

import com.rssll971.fitnessassistantapp.coredata.data.db.entity.ExerciseEntity
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.utils.Mapper

/** Maps data from [ExerciseEntity] to [ExerciseParam]*/
internal class ExerciseEntityToParamMapper: Mapper<ExerciseEntity, ExerciseParam>{
    override fun map(input: ExerciseEntity): ExerciseParam = with(input){
        ExerciseParam(
            name = name,
            imagePath = imagePath,
            description = description,
            id = id
        )
    }
}

/** Maps data from [ExerciseParam] to [ExerciseEntity]*/
internal class ExerciseParamToEntityMapper: Mapper<ExerciseParam, ExerciseEntity>{
    override fun map(input: ExerciseParam): ExerciseEntity = with(input){
        ExerciseEntity(
            name = name,
            imagePath = imagePath,
            description = description,
            id = id
        )
    }

}