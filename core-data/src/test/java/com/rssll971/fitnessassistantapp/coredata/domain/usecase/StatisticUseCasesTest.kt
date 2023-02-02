/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase

import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetAllStatisticUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.GetLastStatisticUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class StatisticUseCasesTest {
    private val repository = mock<StatisticRepository>()
    private val statisticParam = StatisticParam(
        1675355466158L,
        30,
        30,
        false,
        listOf(1,2,3),
        5
    )

    @Test
    fun `Is statistic added to repository`() = runBlocking {
        AddStatisticUseCase(repository).invoke(statisticParam)
        Mockito.verify(repository, Mockito.atLeastOnce()).insertStatistic(statisticParam)
    }

    @Test
    fun `Is the same data returned as in the repository`() = runBlocking {
        val expected = listOf(statisticParam, statisticParam, statisticParam)
        Mockito.`when`(repository.getAllStatistic()).thenReturn(
            flowOf(expected)
        )

        val uc = GetAllStatisticUseCase(repository).invoke()
        uc.collect{
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `Is last statistic returned correctly`() = runBlocking {
        Mockito.`when`(repository.getLastStatistic()).thenReturn(
            flowOf(statisticParam)
        )

        val uc = GetLastStatisticUseCase(repository).invoke()
        uc.collect{
            Assertions.assertEquals(statisticParam, it)
        }
    }
}