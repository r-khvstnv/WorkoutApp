/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2023                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rssll971.fitnessassistantapp.coredata.domain.model.ExerciseParam
import com.rssll971.fitnessassistantapp.coredata.domain.model.StatisticParam
import com.rssll971.fitnessassistantapp.coredata.domain.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.coredata.domain.repository.StatisticRepository
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.exercise.GetAllExercisesUseCase
import com.rssll971.fitnessassistantapp.coredata.domain.usecase.statistic.AddStatisticUseCase
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.OptionsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/*
class FakeExerciseRepo: ExerciseRepository {
    private val exerciseParam = ExerciseParam(
        "ExerciseName",
        "path",
        "desc",
        0
    )

    override suspend fun insertExercise(exerciseParam: ExerciseParam) {

    }

    override suspend fun updateExercise(exerciseParam: ExerciseParam) {

    }

    override suspend fun deleteExercise(exerciseParam: ExerciseParam) {

    }

    override fun getExerciseById(id: Int): Flow<ExerciseParam> {
        return flowOf(exerciseParam)
    }

    override fun getExerciseList(): Flow<List<ExerciseParam>> {
        return flowOf(listOf(exerciseParam))
    }

    override fun getExercisesByIdList(ids: List<Int>): Flow<List<ExerciseParam>> {
        return flowOf(listOf(exerciseParam))
    }
}

class FakeStatisticRepo: StatisticRepository{
    private val stat = StatisticParam(
        0,0,0,false, listOf(1),0
    )
    override suspend fun insertStatistic(statisticParam: StatisticParam) {

    }

    override fun getAllStatistic(): Flow<List<StatisticParam>> {
        return flowOf(listOf(stat))
    }

    override fun getLastStatistic(): Flow<StatisticParam> {
        return flowOf(stat)
    }

}

class OptionsViewModelTest{
    private lateinit var fakeExRepo: FakeExerciseRepo
    private lateinit var fakeStatRepo: FakeStatisticRepo
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase
    private lateinit var addStatisticUseCase: AddStatisticUseCase
    private lateinit var viewModel: OptionsViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        fakeExRepo = FakeExerciseRepo()
        fakeStatRepo = FakeStatisticRepo()
        getAllExercisesUseCase = GetAllExercisesUseCase(fakeExRepo)
        addStatisticUseCase = AddStatisticUseCase(fakeStatRepo)

        viewModel = OptionsViewModel(getAllExercisesUseCase, addStatisticUseCase)
    }

    @Test
    fun increaseExerciseTime(){
        val initExT = viewModel.exerciseTime.getOrAwait()

        viewModel.increaseExerciseTime()
        assertNotEquals(initExT, viewModel.exerciseTime.getOrAwait())
    }

    @Test
    fun increaseRestTime(){
        val initRestT = viewModel.restTime.getOrAwait()

        viewModel.increaseRestTime()
        assertNotEquals(initRestT, viewModel.restTime.getOrAwait())
    }

    @Test
    fun decreaseRestTime(){
        val initRestT = viewModel.restTime.getOrAwait()

        viewModel.decreaseRestTime()
        assertNotEquals(initRestT, viewModel.restTime.getOrAwait())
    }

    @Test
    fun decreaseExerciseTime(){
        val initExT = viewModel.exerciseTime.getOrAwait()

        viewModel.decreaseExerciseTime()
        assertNotEquals(initExT, viewModel.exerciseTime.getOrAwait())
    }

    @Test
    fun setupStatistic(){
        val list = listOf(5)
        val date = 1675345642292L
        val init = viewModel.isStatisticAdded.getOrAwait()

        viewModel.setupStatistic(list, date)
        assertNotEquals( init, viewModel.isStatisticAdded.getOrAwait())
    }
}*/
