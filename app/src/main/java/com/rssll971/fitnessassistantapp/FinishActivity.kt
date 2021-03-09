package com.rssll971.fitnessassistantapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding
    private lateinit var adViewBannerFinish: AdView

    /**
     * Fullscreen Mode
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
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
        /** Create view using Binding**/
        binding = ActivityFinishBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        /** Ads*/
        MobileAds.initialize(this)
        adViewBannerFinish = findViewById(R.id.adView_banner_finish_top)
        adViewBannerFinish.loadAd(AdRequest.Builder().build())

        adViewBannerFinish.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerFinish.loadAd(AdRequest.Builder().build())
            }
        }

        prepareCharts()

        binding.llExit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun prepareCharts(){
        //average duration line chart
        val workoutStatisticDataBaseHandler = WorkoutStatisticDataBaseHandler(this)
        val statisticList = workoutStatisticDataBaseHandler.viewStatisticData()
        if (statisticList.size > 0){
            binding.lineChartWorkoutDuration.visibility = View.VISIBLE
            setupWorkoutDurationLineChart(statisticList)
        }
        else{
            binding.lineChartWorkoutDuration.visibility = View.INVISIBLE
        }

    }
    /**
     * Next method show duration Line Chart
     */
    private fun setupWorkoutDurationLineChart(statisticList: ArrayList<WorkoutStatisticModelClass>){
        val entriesDuration = ArrayList<Entry>()
        var counter = statisticList.size
        for (i in 0 until statisticList.size){
            if (counter <= 10){
                val totalDuration = (statisticList[i].getExerciseDuration() +
                        statisticList[i].getRestDuration()) * statisticList[i].getExerciseAmount()
                entriesDuration.add(Entry(i.toFloat(), totalDuration.toFloat()))
            }
            counter--
        }

        //customize data set
        val ldsDuration = LineDataSet(entriesDuration, getString(R.string.st_workout_duration))
        ldsDuration.mode = LineDataSet.Mode.CUBIC_BEZIER
        //values on peaks
        ldsDuration.setDrawValues(true)
        ldsDuration.valueTextColor = ContextCompat.getColor(this, R.color.myDarkBlue)
        ldsDuration.valueTextSize = 14f
        ldsDuration.valueFormatter = MyValueFormatterToTime()


        ldsDuration.setDrawCircles(true)
        ldsDuration.setCircleColor(ContextCompat.getColor(this, R.color.myOrange))
        //line color and width
        ldsDuration.color = ContextCompat.getColor(this, R.color.myOrange)
        ldsDuration.lineWidth = 3f
        //color under line
        ldsDuration.setDrawFilled(true)
        ldsDuration.fillColor = ContextCompat.getColor(this, R.color.myGreenBlue)

        //add data to chart
        binding.lineChartWorkoutDuration.data = LineData(ldsDuration)

        //make untouchable
        binding.lineChartWorkoutDuration.setTouchEnabled(false)
        binding.lineChartWorkoutDuration.isDragEnabled = true


        //hide all axis labels and grid background
        binding.lineChartWorkoutDuration.xAxis.isEnabled = false
        binding.lineChartWorkoutDuration.axisRight.isEnabled = false
        binding.lineChartWorkoutDuration.axisLeft.isEnabled = false
        binding.lineChartWorkoutDuration.xAxis.setDrawGridLines(false)
        binding.lineChartWorkoutDuration.axisLeft.setDrawGridLines(false)
        binding.lineChartWorkoutDuration.axisRight.setDrawGridLines(false)


        binding.lineChartWorkoutDuration.description.text = ""
        binding.lineChartWorkoutDuration.legend.isEnabled = false
        binding.lineChartWorkoutDuration.setNoDataText("")
    }
}
