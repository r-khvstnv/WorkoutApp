/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.domain.usecase

import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ExerciseUseCasesTest {
    private val repository = mock<ExerciseRepository>()
    private val exerciseParam7 = ExerciseParam(
        "exercise",
        imagePath = "somePath",
        description = "desc",
        id = 7
    )
    private val exerciseParam8 = ExerciseParam(
        "exercise",
        imagePath = "somePath",
        description = "desc",
        id = 8
    )

    @Test
    fun `Is exercise added at least once`() = runBlocking{
        AddExerciseUseCase(repository).invoke(param = exerciseParam7)
        Mockito.verify(repository, Mockito.atLeastOnce()).insertExercise(exerciseParam7)
    }

    @Test
    fun `Is exercise deleted by useCase`() = runBlocking{
        DeleteExerciseUseCase(repository).invoke(param = exerciseParam7)
        Mockito.verify(repository, Mockito.atLeastOnce()).deleteExercise(exerciseParam7)
    }

    @Test
    fun `Is the same data returned as in the repository`() = runBlocking{
        val expected = listOf(exerciseParam7, exerciseParam7, exerciseParam8)
        Mockito.`when`(repository.getExerciseList()).thenReturn(
            flowOf(expected)
        )
        val uc = GetAllExercisesUseCase(repository).invoke()
        uc.collect{
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `Is corresponding data returned by requested Id`() = runBlocking{
        val expected = exerciseParam7
        Mockito.`when`(repository.getExerciseById(id = expected.id)).thenReturn(
            flowOf(expected)
        )

        val uc = GetExerciseByIdUseCase(repository).invoke(expected.id)
        uc.collect{
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `Is requested list of exercises returned by id list`() = runBlocking {
        val ids = listOf(exerciseParam7.id, exerciseParam8.id)
        val expected = listOf(exerciseParam7, exerciseParam8)
        Mockito.`when`(repository.getExercisesByIdList(ids)).thenReturn(
            flowOf(expected)
        )

        val uc = GetExercisesByIdListUseCase(repository).invoke(ids)
        uc.collect{
            Assertions.assertEquals(expected, it)
        }
    }

    @Test
    fun `Is exercise updated once`() = runBlocking {
        UpdateExerciseUseCase(repository).invoke(exerciseParam7)
        Mockito.verify(repository, Mockito.atLeastOnce()).updateExercise(exerciseParam7)
    }
}