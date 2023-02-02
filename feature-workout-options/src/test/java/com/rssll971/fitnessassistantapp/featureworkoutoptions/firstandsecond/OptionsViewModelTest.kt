/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetAllExercisesUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class OptionsViewModelTest {
    private val getAllExercisesUseCase = mock<GetAllExercisesUseCase>()
    private val addStatisticUseCase = mock<AddStatisticUseCase>()
    private lateinit var viewModel: OptionsViewModel

    @BeforeEach
    fun beforeEach(){
        ArchTaskExecutor.getInstance().setDelegate(object: TaskExecutor(){
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

        })
        viewModel = OptionsViewModel(
            getAllExercisesUseCase, addStatisticUseCase
        )

    }
    @AfterEach
    fun tearDown(){
        Mockito.reset(getAllExercisesUseCase)
        Mockito.reset(addStatisticUseCase)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    @Test
    fun `Is rest time increased correctly`(){
        val expected = 90
        viewModel.increaseRestTime()
        val actual = viewModel.restTime.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `Is exercise time increased correctly`(){
        val expected = 90
        viewModel.increaseExerciseTime()
        val actual = viewModel.exerciseTime.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `Is rest time decrease correctly`(){
        val expected = 30
        viewModel.decreaseRestTime()
        val actual = viewModel.restTime.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `Is exercise time decrease correctly`(){
        val expected = 30
        viewModel.decreaseExerciseTime()
        val actual = viewModel.exerciseTime.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `Is rest time not decrease after bound`(){
        val expected = 30
        viewModel.decreaseRestTime()
        viewModel.decreaseRestTime()
        val actual = viewModel.restTime.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `Is exercise time not decrease after bound`(){
        val expected = 30
        viewModel.decreaseExerciseTime()
        viewModel.decreaseExerciseTime()
        val actual = viewModel.exerciseTime.value

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun `Is statistic added correctly`() = runBlocking {
        val list = listOf(1,2,3)
        val time = 1675355466158L
        viewModel.setupStatistic(list, time)
        Mockito.verify(addStatisticUseCase, Mockito.atLeastOnce()).invoke(
            StatisticParam(
            time,
            viewModel.restTime.value!!,
            viewModel.exerciseTime.value!!,
            false,
            list,
            0
        ))
        Assertions.assertEquals(true, viewModel.isStatisticAdded.value)
    }

}