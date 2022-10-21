/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.coredata.utils

import com.rssll971.fitnessassistantapp.coredata.models.ExerciseParam

object DefaultExercises {
    fun getRuExerciseList(): List<ExerciseParam>{
        val burpee = ExerciseParam(
            "Бёрпи с отжиманиями",
            "",
            "Исходное положение: стоя на полу.\n" +
                    "чтобы встать в планку на вытянутых руках. Отожмитесь один раз. " +
                    "Присядьте и положите ладони на пол. В прыжке переместите ноги назад так, " +
                    "чтобы встать в планку на вытянутых руках. Отожмитесь один раз. " +
                    "В прыжке подтяните ноги обратно к рукам и встаньте, вернувшись в исходное положение.",
            0
        )
        val sideBar = ExerciseParam(
            "Динамическая боковая планка",
            "",
            "Исходное положение: боковая планка на предплечьях, " +
                    "локоть расположен ровно под плечевым суставом.\n" +
                    "Слегка поднимите бёдра вверх и задержитесь в таком положении на несколько секунд. " +
                    "Затем медленно опустите таз вниз. При этом старайтесь не касаться пола. " +
                    "Если хотите усложнить упражнение, можете дополнительно удерживать на боку утяжелитель.",
            0
        )
        val lunges = ExerciseParam(
            "Прямые и обратные выпады",
            "",
            "Исходное положение: стоя на полу.\n" +
                    "Шагните правой ногой вперёд и присядьте, согнув её в колене так, " +
                    "чтобы образовался угол в 90 градусов. При этом колено левой ноги не должно коснуться пола. " +
                    "Не забывайте напрягать ягодицы! Затем встаньте, шагните правой ногой назад и снова присядьте, " +
                    "только теперь с опорой на левую ногу. Встаньте и вернитесь в исходное положение. " +
                    "Повторите те же движения, начиная с другой ноги.",
            0
        )
        val kneePlacket = ExerciseParam(
            "Планка с подтягиванием коленей",
            "",
            "Исходное положение: планка на вытянутых руках, живот подтянут, ноги прямые.\n" +
                    "На выдохе подтяните колено к корпусу, отведя в сторону. " +
                    "На вдохе вернитесь в исходное положение и повторите те же движения другой ногой. " +
                    "Для увеличения нагрузки вы можете чередовать подтягивание коленей с отжиманиями.",
            0
        )
        val kneeTwists = ExerciseParam(
            "Скручивания локтем к колену",
            "",
            "Исходное положение: лёжа на спине, ноги подняты и согнуты в коленях, ладони сложены на затылке.\n" +
                    "Слегка приподнимите корпус и потянитесь левым локтем к правой ноге." +
                    "В это же время выпрямите левую ногу, но не касайтесь ею пола. " +
                    "Затем выполните те же движения, скручиваясь другим локтем к противоположной ноге.",
            0
        )

        return listOf(burpee, sideBar, lunges, kneePlacket, kneeTwists)
    }

    fun getEngExerciseList(): List<ExerciseParam>{
        val burpee = ExerciseParam(
            "Burpee with push-ups",
            "",
            "Starting position: standing on the floor. \nSit down and place your palms on the floor. " +
                    "In a jump, move your legs back so that you stand in the bar with outstretched arms. " +
                    "Push up once. In the jump, pull your legs back to your arms and stand back to the starting position.",
            0
        )
        val sideBar = ExerciseParam(
            "Dynamic side bar",
            "",
            "Starting position: side plank on the forearms, the elbow is located exactly under the shoulder joint. \n" +
                    "Lift your hips up slightly and hold in this position for a few seconds. " +
                    "Then slowly lower your pelvis down. When doing this, try not to touch the floor. " +
                    "If you want to complicate the exercise, you can additionally hold the weight on your side.",
            0
        )
        val lunges = ExerciseParam(
            "Direct and reverse lunges",
            "",
            "Starting position: standing on the floor. \nStep your right leg forward and squat down, " +
                    "bending it at the knee so that an angle of 90 degrees is formed. " +
                    "In this case, the knee of the left leg should not touch the floor. " +
                    "Don't forget to tighten your glutes! Then stand up, step back with your right foot and sit down again, " +
                    "only now with support on your left leg. Stand up and return to the starting position. " +
                    "Repeat the same movements starting with the other leg.",
            0
        )
        val kneePlacket = ExerciseParam(
            "Knee placket",
            "",
            "Starting position: plank on outstretched arms, belly tucked up, legs straight. \n" +
                    "As you exhale, pull your knee to the body, pulling it to the side. " +
                    "While inhaling, return to the starting position and repeat the same movements with the other leg. " +
                    "To increase the load, you can alternate knee pulling with push-ups.",
            0
        )
        val kneeTwists = ExerciseParam(
            "Elbow to knee twists",
            "",
            "Starting position: lying on your back, legs raised and bent at the knees, palms folded at the back of the head. \n" +
                    "Raise your torso slightly and stretch your left elbow towards your right leg. " +
                    "At the same time, straighten your left leg, but do not touch the floor with it. " +
                    "Then do the same movements, twisting the other elbow towards the opposite leg.",
            0
        )

        return listOf(burpee, sideBar, lunges, kneePlacket, kneeTwists)
    }
}