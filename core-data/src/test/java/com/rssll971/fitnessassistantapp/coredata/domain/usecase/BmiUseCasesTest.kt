/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase

import com.rssll971.fitnessassistantapp.coredata.domain.model.BmiParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.BmiRepository
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.AddBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.DeleteAllBmiUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.bmi.GetAllBmiUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


class BmiUseCasesTest {
    private val repository = mock<BmiRepository>()
    private val bmiParam = BmiParam(
        1675355466158,
        95f,
        183f,
        26f,
        1
    )

    @AfterEach
    fun tearDown(){
        Mockito.reset(repository)
    }

    @Test
    fun `Is bmi data saved at least once`() = runBlocking{
        AddBmiUseCase(repository).invoke(bmiParam)
        Mockito.verify(repository, Mockito.atLeastOnce()).insertBmi(bmiParam = bmiParam)
    }

    @Test
    fun `Is all bmi entities deleted from repository`() = runBlocking{
        DeleteAllBmiUseCase(repository).invoke()
        /**
         * Check that deleteAllBmi() is called al least once
         * */
        Mockito.verify(repository, Mockito.atLeastOnce()).deleteAllBmi()
    }

    @Test
    fun `Should return the same data as in the repository`() = runBlocking{
        val expected = listOf(bmiParam, bmiParam, bmiParam)
        Mockito.`when`(repository.getBmiList()).thenReturn(
            flowOf(expected)
        )

        val getAllBmiUseCase = GetAllBmiUseCase(repository = repository)

        getAllBmiUseCase.invoke().collect {
            Assertions.assertEquals(expected, it)
        }
    }
}

