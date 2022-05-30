package com.rssll971.fitnessassistantapp.featureexercise.all.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rssll971.fitnessassistantapp.coredata.db.repository.ExerciseRepository
import com.rssll971.fitnessassistantapp.featureexercise.all.AllFragment
import dagger.Component
import kotlin.properties.Delegates

@Component(modules = [AllModule::class], dependencies = [FeatureExerciseDeps::class])
interface AllComponent {
    @Component.Builder
    interface Builder{
        fun deps(featureExerciseDeps: FeatureExerciseDeps): Builder
        fun build(): AllComponent
    }

    fun inject(fragment: AllFragment)
}
/*так как фича не знает о существования апп модуля, му не можем явно объявить его компонент в фиче компоненте.
* по этой причине необходимо депенд от кастомного интерфеса.
* так же необходимо явно указать классы, которые потребуется для дальнейшего инжекта,
* тк опять таки от сюда мы не видим ап компонент.
* и чтобы получить к ним доступ в ап компонете мы также унаследуем этот интерфейс */
interface FeatureExerciseDeps{
    val repositoryExercise: ExerciseRepository
    val viewModelFactory: ViewModelProvider.Factory
}
/*
* интерфейс через который мы будем получать ап компонент.
* тк ап компонент уже унаследовал депс мы воркароунд получим его сюда */
interface FeatureExerciseDepsProvider{
    //ограничиваем геттер в модуле, тк эту блядскую вешь нужно проделывать в каждом модуле
    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: FeatureExerciseDeps

    /*
    * без как работает, но без нее не получим олл компонент
    * понятно что мы предоставляем интерфейс как объект стора, но... замкнутый круг какой то
    * */
    companion object : FeatureExerciseDepsProvider by FeatureExerciseDepsStore
    /*ПОНЯЛ: таким образом чтобы получить реально существуюший инстанс, когда кто то постучиться в него
    * он представиться стором и возьмет существущее значение (###)*/
}
/*
* чтобы все было вообще идеально используем объект, тк это явный сингелтон. он и будет торчать в апп*/
object FeatureExerciseDepsStore: FeatureExerciseDepsProvider{
    /*(###) а вот здесь мы просто служим в качестве конструктора это 1
    и 2 инстанс же придет именно сюда так как выше мы объявляли тольео наши требования*/
    override var deps: FeatureExerciseDeps by Delegates.notNull()
}
/*
* тк мы нам не нужно чтобы она постоянно перерождалась и внезапно получить мемори лик,
* завяжем ее инстанс на отдельную вью модель
* (мы не сможем хранить ее в оригинальной вью моделе тк ее порадит фабрика из инжекта.
*  но надо подумать над переносом в аттач. в теории жц не должен ее убить
* !!!: НЕ возвращаться к этой мысли - УБЬЕТ или поворот или нехватка памяти)*/
internal class AllComponentViewModel : ViewModel(){
    val allComponent = DaggerAllComponent.builder().deps(FeatureExerciseDepsProvider.deps).build()
}