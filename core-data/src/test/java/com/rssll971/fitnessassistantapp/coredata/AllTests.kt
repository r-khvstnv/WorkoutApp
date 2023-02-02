/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata


import com.rssll971.fitnessassistantapp.coredata.domain.usecase.BmiUseCasesTest
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.ExerciseUseCasesTest
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.StatisticUseCasesTest
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite


@Suite
@SelectClasses(
    BmiUseCasesTest::class,
    ExerciseUseCasesTest::class,
    StatisticUseCasesTest::class
)
class AllTests //todo make correct impl