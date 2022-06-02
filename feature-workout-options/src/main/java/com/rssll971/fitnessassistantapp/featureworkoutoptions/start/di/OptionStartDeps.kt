package com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.coredata.db.repository.StatisticRepository
import kotlin.properties.Delegates


/**
 * Interface for implicit dependency on AppComponent
 * (due to feature-module doesn't know anything about app-module).
 * As declared properties listed all methods required for further injection
 * in corresponding feature-module.
 *
 * Note: Should be inherited in AppComponent
 * */
interface OptionStartDeps {
    val viewModelFactory: ViewModelProvider.Factory
    val repositoryStatistic: StatisticRepository
}
/**
 * Interface provides required instances from appComponent
 * */
interface OptionStartDepsProvider{
    //Restrict getter to current module
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: OptionStartDeps
    //Real existed instance will be provided from DepsStore
    companion object: OptionStartDepsProvider by OptionStartDepsStore
}
/**
 * Explicit Singleton which must be assigned in WorkoutApplication
 * */
object OptionStartDepsStore: OptionStartDepsProvider{
    override var deps: OptionStartDeps by Delegates.notNull()
}
/**
 * ViewModel stores all instances of Components.
 * This implementation simplifies Component lifecycle handling.
 * Note: We can't store instances in original (related to Fragment) ViewModel,
 * due to it creates by ViewModelFactory which should be injected too.
 * */
internal class OptionStartComponentViewModel: ViewModel(){
    val optionStartComponent = DaggerOptionStartComponent.builder().deps(OptionStartDepsProvider.deps).build()
}