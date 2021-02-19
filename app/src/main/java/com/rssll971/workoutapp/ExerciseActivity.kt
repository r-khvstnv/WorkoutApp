package com.rssll971.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.workoutapp.databinding.ActivityExerciseBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    //VARIABLES
    //binding
    private lateinit var binding: ActivityExerciseBinding
    //text to speech
    private var textToSpeech: TextToSpeech? = null
    //voice assistant
    private var isVoiceAssistantActivated: Boolean = false
    //rest timer
    private var relaxationTimer: CountDownTimer? = null
    private var relaxationTimerProgress: Int = 1
    //exercise timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseTimerProgress: Int = 1
    //exercise list
    private lateinit var exerciseList: ArrayList<ExerciseModelClass>
    private var currentExerciseIndex = 0
    //adapter for RecyclerView
    private lateinit var exerciseAdapter: ExerciseStatusAdapter
    //finish exercise phrase
    private var exerciseFinishedPhrase: String = "Закончили упражнение"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Create view using Binding**/
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** Get list of exercises */
        exerciseList = prepareExerciseList()
        relaxationTimerProgress = intent!!.getIntExtra("RelaxationTime", 30)
        exerciseTimerProgress = intent!!.getIntExtra("ExerciseTime", 60)
        isVoiceAssistantActivated = intent!!.getBooleanExtra("VoiceAssistant", false)
        //todo implement speech


        /** Text to Speech*/
        textToSpeech = TextToSpeech(this, this)

        //start rest Timer
        setupRestProgress()

        //setup rv
        setupExerciseRecyclerView()
    }

    /** On finish activity**/
    override fun onDestroy() {
        //stop timers
        if (relaxationTimer != null){
            relaxationTimer!!.cancel()
        }

        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
        }

        //stop text to speech
        if (textToSpeech != null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        super.onDestroy()
    }



    /**BLOCK FUNCTIONALITY FOR TIMERS**/
    /**
     * Next fun responsible for timer before starting exercise
     */
    private fun setRestProgress(){
        var timeInMillis: Int = relaxationTimerProgress
        binding.pbTimerProgressRest.max = relaxationTimerProgress

        relaxationTimer = object : CountDownTimer((timeInMillis * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis--

                //show progress
                binding.pbTimerProgressRest.progress = timeInMillis
                binding.tvTimerRest.text = timeInMillis.toString()
            }

            override fun onFinish() {

                setupExerciseTimer()
            }
        }.start()
    }
    /**
     * Next fun reset timer (RestProgress) and calls it
     */
    private fun setupRestProgress(){
        binding.llExerciseScene.visibility = View.GONE
        binding.llRestScene.visibility = View.VISIBLE

        if (relaxationTimer != null){
            relaxationTimer!!.cancel()

        }

        //show next exercise title
        binding.tvUpcomingExercise.text = exerciseList!![currentExerciseIndex].getName()
        setRestProgress()
    }

    /**
     * Next two fun responsible for exercise timer
     */
    private fun setExerciseTimer(){
        var timeInMillis: Int = exerciseTimerProgress
        binding.pbTimerProgressExercise.max = exerciseTimerProgress

        //speak title of exercise
        if (isVoiceAssistantActivated){
            speakOut(binding.tvName.text.toString())
        }


        exerciseTimer = object : CountDownTimer((timeInMillis * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis--
                //show changes
                binding.tvTimerExercise.text = timeInMillis.toString()
                binding.pbTimerProgressExercise.progress = timeInMillis
            }

            override fun onFinish() {
                //mark as finished
                exerciseList!![currentExerciseIndex].setIsFinished(true)

                //increment current exercise position
                currentExerciseIndex++

                //speak that current exercise is finished
                if (isVoiceAssistantActivated){
                    speakOut(exerciseFinishedPhrase)
                }

                //notify RV about changes
                exerciseAdapter.notifyDataSetChanged()

                if (currentExerciseIndex < exerciseList!!.size){
                setupRestProgress()
                }else{
                    showFinishActivity()
                }

            }
        }.start()
    }

    private fun setupExerciseTimer(){
        binding.llRestScene.visibility = View.GONE
        binding.llExerciseScene.visibility = View.VISIBLE

        if (exerciseTimer != null){
            exerciseTimer!!.cancel()

        }

        //show current exercise
        loadExercise(currentExerciseIndex)
        //start timer
        setExerciseTimer()
    }
    /** BLOCK ENDS */


    /**
     * Next fun load exercise to scene
     */
    private fun loadExercise(exerciseIndex: Int){
            binding.tvName.text = exerciseList!![exerciseIndex].getName()
            binding.tvDescription.text = exerciseList!![exerciseIndex].getDescription()

    }


    /**
     * Next two fun responsible for Text to speech
     */
    override fun onInit(status: Int) {
        //check status
        if (status == TextToSpeech.SUCCESS){
            //set RU lang
            val resultSpeech = textToSpeech!!.setLanguage(Locale("ru", "RU"))
            //check result
            if (resultSpeech == TextToSpeech.LANG_MISSING_DATA || resultSpeech == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Not supported")
            }
        }else{
            Log.e("TTS", "Initialization Failed")
        }
    }
    private fun speakOut(text: String){
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    /**
     * Next fun load RecyclerView and add content in it
     */
    private fun setupExerciseRecyclerView(){
        //create rv of type Linear Horizontal
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //pass list
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        //set adapter to rv
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    /**
     * Next fun complete current activity and start FinishActivity
     */
    private fun showFinishActivity(){
        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)
        finish()
    }

    //prepare exercise list
    private fun prepareExerciseList(): ArrayList<ExerciseModelClass>{
        /**
         * Next lines responsible for extracting users exercises and default in rv
         */
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        val emcList = dataBaseHandler.viewUsersExercises()
        val defaultEmcList: ArrayList<ExerciseModelClass> = ExerciseModelClass.defaultExerciseList()
        for (i in 0 until defaultEmcList.size){
            emcList.add(defaultEmcList[i])
        }

        var formedExerciseList = ArrayList<ExerciseModelClass>()
        val userSelectedExercises = intent.getStringArrayListExtra("FormedList")
        for (i in 0 until userSelectedExercises!!.size){
            for (j in 0 until emcList.size){
                if (userSelectedExercises[i] == emcList[j].getName()){
                    formedExerciseList.add(emcList[j])
                }
            }
        }

        return formedExerciseList
    }
}