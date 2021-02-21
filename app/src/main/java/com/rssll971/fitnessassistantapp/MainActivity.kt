package com.rssll971.fitnessassistantapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //main ad banner
    private lateinit var adViewBannerMain: AdView

    //relaxation time
    private var relaxationTime: Int = 30
    //exercise time
    private var exerciseTime: Int = 30
    //default exercise list
    private lateinit var emcList: ArrayList<ExerciseModelClass>
    //exercise list
    private var formedExerciseList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Create view using Binding**/
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** Ads*/
        MobileAds.initialize(this)
        adViewBannerMain = findViewById(R.id.adView_banner_main)
        adViewBannerMain.loadAd(AdRequest.Builder().build())

        adViewBannerMain.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerMain.loadAd(AdRequest.Builder().build())
            }
        }



        //start exercises
        binding.llStart.setOnClickListener {
            showExerciseSelectionDialog()
        }


        //bmi calculator
        binding.llBmi.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        //activities
        binding.llActivities.setOnClickListener {
            val intent = Intent(this, ActivitiesCatalogActivity::class.java)
            startActivity(intent)
        }

        //settings
        binding.llInfo.setOnClickListener {
            showInfoDialog()
        }


    }


    //show dialog with parameters
    private fun showExerciseSelectionDialog(){
        val dialogExerciseSelection = Dialog(this)
        dialogExerciseSelection.setContentView(R.layout.dialog_select_exercise)
        dialogExerciseSelection.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //all buttons
        val rbVoiceOn = dialogExerciseSelection.findViewById<RadioButton>(R.id.rb_voice_on)

        val ivMinusRelaxationTime = dialogExerciseSelection.findViewById<ImageView>(R.id.iv_minus_relaxation_time)
        val ivPlusRelaxationTime = dialogExerciseSelection.findViewById<ImageView>(R.id.iv_plus_relaxation_time)
        val tvRelaxationTime = dialogExerciseSelection.findViewById<TextView>(R.id.tv_relaxation_time)
        tvRelaxationTime.text = relaxationTime.toString()

        val ivMinusExerciseTime = dialogExerciseSelection.findViewById<ImageView>(R.id.iv_minus_exercise_time)
        val ivPlusExerciseTime = dialogExerciseSelection.findViewById<ImageView>(R.id.iv_plus_exercise_time)
        val tvExerciseTime = dialogExerciseSelection.findViewById<TextView>(R.id.tv_exercise_time)
        tvExerciseTime.text = exerciseTime.toString()

        /**
         * Next lines responsible for extracting users exercises and default in rv
         */
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        emcList = dataBaseHandler.viewUsersExercises()
        val defaultEmcList: ArrayList<ExerciseModelClass> = ExerciseModelClass.defaultExerciseList()
        for (i in 0 until defaultEmcList.size){
            emcList.add(defaultEmcList[i])
        }
        //equalize size of user array
        val rvSelectExercises = dialogExerciseSelection.findViewById<RecyclerView>(R.id.rv_select_activities)
        rvSelectExercises.layoutManager = LinearLayoutManager(this)
        val userExercisesAdapter = UserExercisesAdapter(this, emcList)
        rvSelectExercises.adapter = userExercisesAdapter


        val llStart = dialogExerciseSelection.findViewById<LinearLayout>(R.id.ll_start_session)

        dialogExerciseSelection.show()

        //show result on time changes
        ivMinusRelaxationTime.setOnClickListener {
            if (relaxationTime > 30){
                relaxationTime -= 30
                tvRelaxationTime.text = relaxationTime.toString()
            }
        }
        ivPlusRelaxationTime.setOnClickListener {
            relaxationTime += 30
            tvRelaxationTime.text = relaxationTime.toString()
        }
        ivMinusExerciseTime.setOnClickListener {
            if(exerciseTime > 30){
                exerciseTime -= 30
                tvExerciseTime.text = exerciseTime.toString()
            }
        }
        ivPlusExerciseTime.setOnClickListener {
            exerciseTime += 30
            tvExerciseTime.text = exerciseTime.toString()
        }

        llStart.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("FormedList", formedExerciseList)
            intent.putExtra("RelaxationTime", relaxationTime)
            intent.putExtra("ExerciseTime", exerciseTime)
            intent.putExtra("VoiceAssistant", rbVoiceOn.isChecked)
            startActivity(intent)
            dialogExerciseSelection.dismiss()
        }
    }

    fun prepareExerciseList(position: Int, isNeededAdd: Boolean){
        if (isNeededAdd){
            formedExerciseList.add(emcList[position].getName())

        }
        else{
            for (i in 0 until  formedExerciseList.size){
                if (formedExerciseList[i] == emcList[position].getName()){
                    formedExerciseList.removeAt(i)
                    break
                }
            }
        }
    }


    private fun showInfoDialog(){
        val dialogInfo = Dialog(this)
        dialogInfo.setContentView(R.layout.dialog_info)
        dialogInfo.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnBack = dialogInfo.findViewById<Button>(R.id.btn_back)

        dialogInfo.show()

        btnBack.setOnClickListener {
            dialogInfo.dismiss()
        }
    }


}