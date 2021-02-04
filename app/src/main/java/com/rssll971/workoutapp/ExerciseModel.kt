package com.rssll971.workoutapp

class ExerciseModel (private var _id: Int,
                     private var _name: String,
                     private var _description: String,
                     private var _isFinished: Boolean){

    /** ID */
    fun setId(id: Int){
        _id = id
    }
    fun getId() : Int {
        return _id
    }

    /** Name */
    fun setName(name: String){
        _name = name
    }
    fun getName() : String{
        return _name
    }

    /** Description */
    fun setDescription(description: String){
        _description = description
    }
    fun getDescription() : String{
        return _description
    }

    /** Finish marking*/
    fun setIsFinished(isFinished: Boolean){
        _isFinished = isFinished
    }
    fun getIsFinished(): Boolean{
        return _isFinished
    }

    companion object{
        fun defaultExerciseList() : ArrayList<ExerciseModel>{
            //List with exercises
            val exerciseList = ArrayList<ExerciseModel>()
            //Different exercises
            val burpee = ExerciseModel(1, "Бёрпи с отжиманиями",
                "Присядьте и положите ладони на пол. В прыжке переместите ноги назад так, " +
                        "чтобы встать в планку на вытянутых руках. Отожмитесь один раз. " +
                        "В прыжке подтяните ноги обратно к рукам и встаньте, вернувшись в исходное положение.",
                false)
            exerciseList.add(burpee)

            val sideBar = ExerciseModel(2, "Динамическая боковая планка",
                "Исходное положение: боковая планка на предплечьях, " +
                        "локоть расположен ровно под плечевым суставом.\n" +
                        "Слегка поднимите бёдра вверх и задержитесь в таком положении на несколько секунд. " +
                        "Затем медленно опустите таз вниз. При этом старайтесь не касаться пола. " +
                        "Если хотите усложнить упражнение, можете дополнительно удерживать на боку утяжелитель.",
                false)
            exerciseList.add(sideBar)

            val lunges = ExerciseModel(3, "Прямые и обратные выпады",
                "Шагните правой ногой вперёд и присядьте, согнув её в колене так, " +
                        "чтобы образовался угол в 90 градусов. При этом колено левой ноги не должно коснуться пола. " +
                        "Не забывайте напрягать ягодицы! Затем встаньте, шагните правой ногой назад и снова присядьте, " +
                        "только теперь с опорой на левую ногу. Встаньте и вернитесь в исходное положение. " +
                        "Повторите те же движения, начиная с другой ноги.",
                false)
            exerciseList.add(lunges)

            val kneePlacket = ExerciseModel(4, "Планка с подтягиванием коленей",
                "На выдохе подтяните колено к корпусу, отведя в сторону. " +
                        "На вдохе вернитесь в исходное положение и повторите те же движения другой ногой. " +
                        "Для увеличения нагрузки вы можете чередовать подтягивание коленей с отжиманиями.",
                false)
            exerciseList.add(kneePlacket)

            val kneeTwists = ExerciseModel(5, "Скручивания локтем к колену",
                "Слегка приподнимите корпус и потянитесь левым локтем к правой ноге. " +
                        "В это же время выпрямите левую ногу, но не касайтесь ею пола. " +
                        "Затем выполните те же движения, скручиваясь другим локтем к противоположной ноге.",
                false)
            exerciseList.add(kneeTwists)


            return exerciseList
        }
    }
}
