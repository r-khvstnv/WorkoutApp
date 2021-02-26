package com.rssll971.fitnessassistantapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.os.ConfigurationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //main ad banner
    private lateinit var adViewBannerMain: AdView
    //default exercise list
    private lateinit var emcList: ArrayList<ExerciseModelClass>
    //bmi history
    private lateinit var bmiDataBaseHandler: BmiDataBaseHandler
    private lateinit var bmiList: ArrayList<BmiHistoryModelClass>
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


        bmiDataBaseHandler = BmiDataBaseHandler(this)
        bmiList = bmiDataBaseHandler.viewBmiResult()
        if (bmiList.size > 0){
            binding.tvPDataMessage.visibility = View.INVISIBLE
            binding.tvWeightLabel.visibility = View.VISIBLE
            binding.lineChart.visibility = View.VISIBLE
            setupLineChart()
        }
        else{
            binding.tvPDataMessage.visibility = View.VISIBLE
            binding.tvWeightLabel.visibility = View.INVISIBLE
            binding.lineChart.visibility = View.INVISIBLE
        }



        /** All clickable items*/
        //start exercises
        binding.llStart.setOnClickListener {
            showExerciseSelectionDialog()
        }

        //bmi calculator
        binding.llBmi.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
            finish()
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


    /**
     * Next method show Line Chart
     */
    private fun setupLineChart(){
        val entriesWeight = ArrayList<Entry>()
        var counter = bmiList.size
        for (i in 0 until bmiList.size){
            if (counter <= 10){
                entriesWeight.add(Entry(i.toFloat(), bmiList[i].getWeight()))
            }
            counter--
        }

        //customize data set
        val lineDataSetWeight = LineDataSet(entriesWeight, getString(R.string.st_weight))
        lineDataSetWeight.mode = LineDataSet.Mode.CUBIC_BEZIER
        //values on peaks
        lineDataSetWeight.setDrawValues(true)
        lineDataSetWeight.valueTextColor = ContextCompat.getColor(this, R.color.myDarkBlue)
        lineDataSetWeight.valueTextSize = 14f


        lineDataSetWeight.setDrawCircles(true)
        lineDataSetWeight.setCircleColor(ContextCompat.getColor(this, R.color.myOrange))
        //line color and width
        lineDataSetWeight.color = ContextCompat.getColor(this, R.color.myOrange)
        lineDataSetWeight.lineWidth = 3f
        //color under line
        lineDataSetWeight.setDrawFilled(true)
        lineDataSetWeight.fillColor = ContextCompat.getColor(this, R.color.myGreenBlue)

        //add data to chart
        binding.lineChart.data = LineData(lineDataSetWeight)

        //make untouchable
        binding.lineChart.setTouchEnabled(false)
        binding.lineChart.isDragEnabled = true

        //binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM // show dates at the bottom
        //hide all axis labels and grid background
        binding.lineChart.xAxis.isEnabled = false
        binding.lineChart.axisRight.isEnabled = false
        binding.lineChart.axisLeft.isEnabled = false
        binding.lineChart.xAxis.setDrawGridLines(false);
        binding.lineChart.axisLeft.setDrawGridLines(false);
        binding.lineChart.axisRight.setDrawGridLines(false);


        binding.lineChart.description.text = ""
        binding.lineChart.legend.isEnabled = false
        //binding.lineChart.description.textColor = ContextCompat.getColor(this, R.color.myOrange)
        //binding.lineChart.legend.textColor = ContextCompat.getColor(this, R.color.myOrange)
        //binding.lineChart.axisLeft.textColor = ContextCompat.getColor(this, R.color.myOrange)
        //binding.lineChart.xAxis.textColor = ContextCompat.getColor(this, R.color.myOrange)
        binding.lineChart.setNoDataText("")

    }


    /**
     * Next method show dialog for exercise selection and other parameters
     */
    private fun showExerciseSelectionDialog(){
        val dialogExerciseSelection = Dialog(this)
        dialogExerciseSelection.setContentView(R.layout.dialog_select_exercise)
        dialogExerciseSelection.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //relaxation time
        var relaxationTime: Int = 30
        //exercise time
        var exerciseTime: Int = 30

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
         * Next lines responsible for extracting users and default exercises in RecyclerView
         */
        //Below lines written exactly in that order,
        // so that in the future default list will be at the bottom
        emcList = if (Locale.getDefault().language == "ru"){
            //set RU lang list
            ExerciseModelClass.defaultRuExerciseList()
        } else{
            ExerciseModelClass.defaultEngExerciseList()
        }
        val dataBaseHandler = ExerciseDataBaseHandler(this)
        emcList.addAll(dataBaseHandler.viewUsersExercises())



        /** Setup RecyclerView*/
        val rvSelectExercises = dialogExerciseSelection.findViewById<RecyclerView>(R.id.rv_select_activities)
        rvSelectExercises.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)
        val userExercisesAdapter = UserExercisesAdapter(this, emcList)
        rvSelectExercises.adapter = userExercisesAdapter
        //smooth scroll to top
        rvSelectExercises.smoothScrollToPosition(userExercisesAdapter.itemCount - 1)



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
            if (relaxationTime <= 600) {
                relaxationTime += 30
                tvRelaxationTime.text = relaxationTime.toString()
            }
        }
        ivMinusExerciseTime.setOnClickListener {
            if(exerciseTime > 30){
                exerciseTime -= 30
                tvExerciseTime.text = exerciseTime.toString()
            }
        }
        ivPlusExerciseTime.setOnClickListener {
            if (exerciseTime < 600) {
                exerciseTime += 30
                tvExerciseTime.text = exerciseTime.toString()
            }
        }

        //start new activity
        llStart.setOnClickListener {
            if (formedExerciseList.isEmpty()){
                Toast.makeText(this, getString(R.string.st_choose_exercise), Toast.LENGTH_SHORT).show()
            }
            else {
                /**
                 * Start new activity with all parameters and chosen exercises
                 */
                val intent = Intent(this, ExerciseActivity::class.java)
                intent.putExtra("FormedList", formedExerciseList)
                intent.putExtra("RelaxationTime", relaxationTime)
                intent.putExtra("ExerciseTime", exerciseTime)
                intent.putExtra("VoiceAssistant", rbVoiceOn.isChecked)
                startActivity(intent)
                dialogExerciseSelection.dismiss()
                formedExerciseList.clear()
            }
        }
    }

    /**
     * Next method prepare exercise list
     *
     * Called from UserExerciseAdapter
     *
     * Method based on saving chosen exercise names, due to don't send all list in new activity
     */
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

    /**
     * Next method show info dialog
     */
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