package com.rssll971.fitnessassistantapp.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.*
import com.rssll971.fitnessassistantapp.adapters.ExerciseStatusAdapter
import com.rssll971.fitnessassistantapp.databasehandlers.WorkoutStatisticDataBaseHandler
import com.rssll971.fitnessassistantapp.databinding.ActivityExerciseBinding
import com.rssll971.fitnessassistantapp.models.ExerciseModel
import com.rssll971.fitnessassistantapp.models.WorkoutStatisticModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    /**VARIABLES*/
    private lateinit var binding: ActivityExerciseBinding
    //text to speech
    private var textToSpeech: TextToSpeech? = null
    //voice assistant
    private var isVoiceAssistantActivated: Boolean = false
    //rest timer
    private var relaxationTimer: CountDownTimer? = null
    private var relaxationTimerProgress: Int = 5
    //exercise timer
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseTimerProgress: Int = 5
    //exercise list
    private lateinit var exerciseList: ArrayList<ExerciseModel>
    private var currentExerciseIndex = 0
    //adapter for RecyclerView
    private lateinit var exerciseAdapter: ExerciseStatusAdapter
    //ads
    private lateinit var adViewBannerRelaxation: AdView
    private lateinit var adViewBannerCurrentExercise: AdView
    /** Fullscreen Mode*/
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** Get all selected options for workout*/
        if (intent.hasExtra("FormedList")){
            exerciseList =
                intent!!.getParcelableArrayListExtra("FormedList")!!
            relaxationTimerProgress =
                intent!!.getIntExtra("RelaxationTime", 30)
            exerciseTimerProgress =
                intent!!.getIntExtra("ExerciseTime", 60)
            isVoiceAssistantActivated =
                intent!!.getBooleanExtra("VoiceAssistant", false)
        }


        /** Ads*/
        MobileAds.initialize(this)
        adViewBannerRelaxation = findViewById(R.id.adView_banner_relaxation)
        adViewBannerCurrentExercise = findViewById(R.id.adView_banner_current_exercise)
        adViewBannerRelaxation.loadAd(AdRequest.Builder().build())
        adViewBannerCurrentExercise.loadAd(AdRequest.Builder().build())

        adViewBannerRelaxation.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerRelaxation.loadAd(AdRequest.Builder().build())
            }
        }
        adViewBannerCurrentExercise.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerCurrentExercise.loadAd(AdRequest.Builder().build())
            }
        }


        /** Text to Speech*/
        textToSpeech = TextToSpeech(this, this)
        /**Start rest timer*/
        setupRelaxationTimer()
        /**Setup rv with exercise numbers*/
        setupExerciseRecyclerView()
    }


    override fun onDestroy() {
        /**Stop all timers and text to speech onDestroy*/
        if (relaxationTimer != null){
            relaxationTimer!!.cancel()
        }

        if (exerciseTimer != null){
            exerciseTimer!!.cancel()
        }


        if (textToSpeech != null){
            textToSpeech!!.stop()
            textToSpeech!!.shutdown()
        }

        super.onDestroy()
    }


    /**BLOCK FUNCTIONALITY FOR TIMERS**/
    /**
     * Next method responsible for timer before starting exercise*/
    private fun setRelaxationTimer(){
        var timeInMillis: Int = relaxationTimerProgress
        binding.pbTimerProgressRest.max = relaxationTimerProgress

        relaxationTimer =
            object : CountDownTimer((timeInMillis * 1000).toLong(), 1000){
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
     * Next method changes layout visibility from exercise to rest
     * and resets timer (RestProgress)
     */
    private fun setupRelaxationTimer(){
        binding.llExerciseScene.visibility = View.GONE
        binding.llRestScene.visibility = View.VISIBLE

        if (relaxationTimer != null){
            relaxationTimer!!.cancel()

        }

        //show next exercise title
        binding.tvUpcomingExercise.text = exerciseList[currentExerciseIndex].name
        //smooth scroll to next exercise status
        if (currentExerciseIndex < exerciseList.size){
            binding.rvExerciseStatus.smoothScrollToPosition(currentExerciseIndex)
        }

        setRelaxationTimer()
    }

    /**
     * Next two methods responsible for exercise timer
     */
    private fun setExerciseTimer(){
        var timeInMillis: Int = exerciseTimerProgress
        binding.pbTimerProgressExercise.max = exerciseTimerProgress

        //speak title of exercise
        if (isVoiceAssistantActivated){
            speakOut(binding.tvName.text.toString())
        }


        exerciseTimer =
            object : CountDownTimer((timeInMillis * 1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis--
                //show changes
                binding.tvTimerExercise.text = timeInMillis.toString()
                binding.pbTimerProgressExercise.progress = timeInMillis
            }

            override fun onFinish() {
                //mark as finished
                exerciseList[currentExerciseIndex].isFinished = true

                //increment current exercise position
                currentExerciseIndex++

                //speak that current exercise is finished
                if (isVoiceAssistantActivated){
                    speakOut(getString(R.string.st_exercise_completed_phrase))
                }

                //notify RV about changes
                exerciseAdapter.notifyDataSetChanged()

                if (currentExerciseIndex < exerciseList.size){
                    setupRelaxationTimer()
                } else{
                    addStatisticData()
                    showFinishActivity()
                    finish()
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
     * Next method loads exercise to layout
     */
    private fun loadExercise(exerciseIndex: Int){
        binding.tvName.text = exerciseList[exerciseIndex].name
        binding.tvDescription.text = exerciseList[exerciseIndex].description

        //load image
        if (exerciseList[exerciseIndex].imagePath == getString(R.string.st_empty_path)){
            binding.ivUserExerciseImage.visibility = View.GONE
            binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        } else{
            binding.ivUserExerciseImage.visibility = View.VISIBLE
            binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            //targetSize
            Glide.with(this)
                .load(exerciseList[exerciseIndex].imagePath)
                .fitCenter().into(binding.ivUserExerciseImage)
        }
    }

    /**
     * Next two methods responsible for Text to speech
     */
    override fun onInit(status: Int) {
        //check status
        if (status == TextToSpeech.SUCCESS){
            //set locale for voice assistant
            val resultSpeech = if (Locale.getDefault().language == "ru"){
                //set RU lang
                textToSpeech!!.setLanguage(Locale("ru", "RU"))
            } else{
                textToSpeech!!.setLanguage(Locale(Locale.ENGLISH.language))
            }

            //check result
            if (resultSpeech == TextToSpeech.LANG_MISSING_DATA
                || resultSpeech == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.i("TTS", "Not supported")
            }
        } else
            Log.e("TTS", "Initialization Failed")
    }
    private fun speakOut(text: String){
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    /**
     * Next method loads RecyclerView and add content in it
     */
    private fun setupExerciseRecyclerView(){
        //create rv of type Linear Horizontal
        binding.rvExerciseStatus.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        //pass list
        exerciseAdapter = ExerciseStatusAdapter(exerciseList, this)
        //set adapter to rv
        binding.rvExerciseStatus.adapter = exerciseAdapter
    }

    /**
     * Next method complete current activity and start FinishActivity
     */
    private fun showFinishActivity(){
        val intent = Intent(this, FinishActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     * Next method add statistic data in database
     */
    private fun addStatisticData(){
        val dataBaseHandler = WorkoutStatisticDataBaseHandler(this)
        val currentDate =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val statisticModel =
            WorkoutStatisticModel(0,
                currentDate,
                relaxationTimerProgress,
                exerciseTimerProgress,
                exerciseList.size)
        dataBaseHandler.addStatisticData(statisticModel)
    }
}