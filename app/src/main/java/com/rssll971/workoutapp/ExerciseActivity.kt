package com.rssll971.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.rssll971.workoutapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    //VARIABLES
    //binding
    private lateinit var binding: ActivityExerciseBinding
    //rest timer
    private var restTimer: CountDownTimer? = null
    private var restTimerProgress: Int = 10
    //exercise timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseTimerProgress: Int = 30
    //exercise list
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Create view using Binding**/
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //start rest Timer
        setupRestProgress()

        /** Get list of exercises */
        exerciseList = ExerciseModel.defaultExerciseList()
    }

    /** On finish activity**/
    override fun onDestroy() {
        restTimer!!.cancel()
        exerciseTimer!!.cancel()
        restTimerProgress = 10
        exerciseTimerProgress = 30
        super.onDestroy()
    }

    /**BLOCK FUNCTIONALITY FOR TIMERS**/
    /**
     * Next fun responsible for timer before starting exercise
     */
    private fun setRestProgress(){
        restTimer = object : CountDownTimer((restTimerProgress * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                restTimerProgress--

                //show progress
                binding.pbTimerProgress.progress = restTimerProgress
                binding.tvTimer.text = restTimerProgress.toString()
            }

            override fun onFinish() {
                //TODO start exercise
                Toast.makeText(this@ExerciseActivity,
                    "${exerciseList?.get(0)?.getName()}", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }
    /**
     * Next fun reset timer (RestProgress) and calls it
     */
    private fun setupRestProgress(){
        if (restTimer != null){
            restTimer!!.cancel()
            restTimerProgress = 10
        }
        else{}

        setRestProgress()
    }

    /**
     * Next two fun responsible for exercise timer
     */
    private fun setExerciseTimer(){
        exerciseTimer = object : CountDownTimer((exerciseTimerProgress * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseTimerProgress--
                //TODO show changes
                //binding.tvTimer.text = exerciseTimerProgress.toString()
                //binding.pbTimerProgress.progress = exerciseTimerProgress
            }

            override fun onFinish() {
                //increment current exercise position
                currentExercisePosition++
                TODO("Not yet implemented")
            }
        }
    }

    private fun setupExerciseTimer(){
        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseTimer = null
        }
        else{}
        //start timer
        setExerciseTimer()
    }
    /** BLOCK ENDS */
}