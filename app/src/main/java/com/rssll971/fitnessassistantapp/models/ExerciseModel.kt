package com.rssll971.fitnessassistantapp.models

import android.os.Parcel
import android.os.Parcelable


data class ExerciseModel (val id: Int = 0,
                          val name: String = "",
                          val imagePath: String = "none",
                          val description: String = "",
                          var isFinished: Boolean = false) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(imagePath)
        parcel.writeString(description)
        parcel.writeByte(if (isFinished) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExerciseModel> {
        override fun createFromParcel(parcel: Parcel): ExerciseModel {
            return ExerciseModel(parcel)
        }

        override fun newArray(size: Int): Array<ExerciseModel?> {
            return arrayOfNulls(size)
        }


        /** default exercises on RU lang */
        fun defaultRuExerciseList() : ArrayList<ExerciseModel>{
            //List with exercises
            val exerciseList = ArrayList<ExerciseModel>()
            //Different exercises
            val burpee = ExerciseModel(1, "Бёрпи с отжиманиями", "none",
                "Исходное положение: стоя на полу.\n" +
                        "чтобы встать в планку на вытянутых руках. Отожмитесь один раз. " +
                        "Присядьте и положите ладони на пол. В прыжке переместите ноги назад так, " +
                        "чтобы встать в планку на вытянутых руках. Отожмитесь один раз. " +
                        "В прыжке подтяните ноги обратно к рукам и встаньте, вернувшись в исходное положение.",
                false)
            exerciseList.add(burpee)

            val sideBar = ExerciseModel(2, "Динамическая боковая планка", "none",
                "Исходное положение: боковая планка на предплечьях, " +
                        "локоть расположен ровно под плечевым суставом.\n" +
                        "Слегка поднимите бёдра вверх и задержитесь в таком положении на несколько секунд. " +
                        "Затем медленно опустите таз вниз. При этом старайтесь не касаться пола. " +
                        "Если хотите усложнить упражнение, можете дополнительно удерживать на боку утяжелитель.",
                false)
            exerciseList.add(sideBar)

            val lunges = ExerciseModel(3, "Прямые и обратные выпады", "none",
                "Исходное положение: стоя на полу.\n" +
                        "Шагните правой ногой вперёд и присядьте, согнув её в колене так, " +
                        "чтобы образовался угол в 90 градусов. При этом колено левой ноги не должно коснуться пола. " +
                        "Не забывайте напрягать ягодицы! Затем встаньте, шагните правой ногой назад и снова присядьте, " +
                        "только теперь с опорой на левую ногу. Встаньте и вернитесь в исходное положение. " +
                        "Повторите те же движения, начиная с другой ноги.",
                false)
            exerciseList.add(lunges)

            val kneePlacket = ExerciseModel(4, "Планка с подтягиванием коленей", "none",
                "Исходное положение: планка на вытянутых руках, живот подтянут, ноги прямые.\n" +
                        "На выдохе подтяните колено к корпусу, отведя в сторону. " +
                        "На вдохе вернитесь в исходное положение и повторите те же движения другой ногой. " +
                        "Для увеличения нагрузки вы можете чередовать подтягивание коленей с отжиманиями.",
                false)
            exerciseList.add(kneePlacket)

            val kneeTwists = ExerciseModel(5, "Скручивания локтем к колену", "none",
                "Исходное положение: лёжа на спине, ноги подняты и согнуты в коленях, ладони сложены на затылке.\n" +
                        "Слегка приподнимите корпус и потянитесь левым локтем к правой ноге." +
                        "В это же время выпрямите левую ногу, но не касайтесь ею пола. " +
                        "Затем выполните те же движения, скручиваясь другим локтем к противоположной ноге.",
                false)
            exerciseList.add(kneeTwists)


            return exerciseList
        }

        /** default exercises on ENG lang */
        fun defaultEngExerciseList() : ArrayList<ExerciseModel>{
            //List with exercises
            val exerciseList = ArrayList<ExerciseModel>()
            //Different exercises
            val burpee = ExerciseModel(1, "Burpee with push-ups", "none",
                "Starting position: standing on the floor. \nSit down and place your palms on the floor. " +
                        "In a jump, move your legs back so that you stand in the bar with outstretched arms. " +
                        "Push up once. In the jump, pull your legs back to your arms and stand back to the starting position.",
                false)
            exerciseList.add(burpee)

            val sideBar = ExerciseModel(2, "Dynamic side bar", "none",
                "Starting position: side plank on the forearms, the elbow is located exactly under the shoulder joint. \n" +
                        "Lift your hips up slightly and hold in this position for a few seconds. " +
                        "Then slowly lower your pelvis down. When doing this, try not to touch the floor. " +
                        "If you want to complicate the exercise, you can additionally hold the weight on your side.",
                false)
            exerciseList.add(sideBar)

            val lunges = ExerciseModel(3, "Direct and reverse lunges", "none",
                "Starting position: standing on the floor. \nStep your right leg forward and squat down, " +
                        "bending it at the knee so that an angle of 90 degrees is formed. " +
                        "In this case, the knee of the left leg should not touch the floor. " +
                        "Don't forget to tighten your glutes! Then stand up, step back with your right foot and sit down again, " +
                        "only now with support on your left leg. Stand up and return to the starting position. " +
                        "Repeat the same movements starting with the other leg.",
                false)
            exerciseList.add(lunges)

            val kneePlacket = ExerciseModel(4, "Knee placket", "none",
                "Starting position: plank on outstretched arms, belly tucked up, legs straight. \n" +
                        "As you exhale, pull your knee to the body, pulling it to the side. " +
                        "While inhaling, return to the starting position and repeat the same movements with the other leg. " +
                        "To increase the load, you can alternate knee pulling with push-ups.",
                false)
            exerciseList.add(kneePlacket)

            val kneeTwists = ExerciseModel(5, "Elbow to knee twists", "none",
                "Starting position: lying on your back, legs raised and bent at the knees, palms folded at the back of the head. \n" +
                        "Raise your torso slightly and stretch your left elbow towards your right leg. " +
                        "At the same time, straighten your left leg, but do not touch the floor with it. " +
                        "Then do the same movements, twisting the other elbow towards the opposite leg.",
                false)
            exerciseList.add(kneeTwists)


            return exerciseList
        }
    }

}
