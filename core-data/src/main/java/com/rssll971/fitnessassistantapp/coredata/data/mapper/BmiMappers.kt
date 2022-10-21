/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.data.mapper

import com.rssll971.fitnessassistantapp.coredata.data.db.entity.BmiEntity
import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.utils.Mapper

internal class BmiEntityToParamMapper: Mapper<BmiEntity, BmiParam>{
    override fun map(input: BmiEntity): BmiParam = with(input){
        BmiParam(
            date = date,
            weight = weight,
            height = height,
            bmiIndex = bmiIndex,
            id = id
        )
    }

}

internal class BmiParamToEntityMapper: Mapper<BmiParam, BmiEntity>{
    override fun map(input: BmiParam): BmiEntity = with(input){
        BmiEntity(
            date = date,
            weight = weight,
            height = height,
            bmiIndex = bmiIndex,
            id = id
        )
    }

}