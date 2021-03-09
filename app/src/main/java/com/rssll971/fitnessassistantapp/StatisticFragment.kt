package com.rssll971.fitnessassistantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.rssll971.fitnessassistantapp.databinding.FragmentStatisticBinding

class StatisticFragment : Fragment() {
    private var _binding: FragmentStatisticBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * Line Chart
         */
        prepareCharts()
    }



    private fun prepareCharts(){
        //weight line chart
        val bmiDataBaseHandler = BmiDataBaseHandler(requireContext())
        val bmiList = bmiDataBaseHandler.viewBmiResult()
        if (bmiList.size > 0){
            binding.lineChartWeight.visibility = View.VISIBLE
            setupWeightLineChart(bmiList)
        }
        else{
            binding.lineChartWeight.visibility = View.INVISIBLE
        }

        //average duration line chart
        val workoutStatisticDataBaseHandler = WorkoutStatisticDataBaseHandler(requireContext())
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
     * Next method show weight Line Chart
     */
    private fun setupWeightLineChart(bmiList: ArrayList<BmiHistoryModelClass>){
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
        lineDataSetWeight.valueTextColor = ContextCompat.getColor(requireContext(), R.color.myDarkBlue)
        lineDataSetWeight.valueTextSize = 14f


        lineDataSetWeight.setDrawCircles(true)
        lineDataSetWeight.setCircleColor(ContextCompat.getColor(requireContext(), R.color.myOrange))
        //line color and width
        lineDataSetWeight.color = ContextCompat.getColor(requireContext(), R.color.myOrange)
        lineDataSetWeight.lineWidth = 3f
        //color under line
        lineDataSetWeight.setDrawFilled(true)
        lineDataSetWeight.fillColor = ContextCompat.getColor(requireContext(), R.color.myGreenBlue)

        //add data to chart
        binding.lineChartWeight.data = LineData(lineDataSetWeight)

        //make untouchable
        binding.lineChartWeight.setTouchEnabled(false)
        binding.lineChartWeight.isDragEnabled = true

        //binding.lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM // show dates at the bottom
        //hide all axis labels and grid background
        binding.lineChartWeight.xAxis.isEnabled = false
        binding.lineChartWeight.axisRight.isEnabled = false
        binding.lineChartWeight.axisLeft.isEnabled = false
        binding.lineChartWeight.xAxis.setDrawGridLines(false);
        binding.lineChartWeight.axisLeft.setDrawGridLines(false);
        binding.lineChartWeight.axisRight.setDrawGridLines(false);


        binding.lineChartWeight.description.text = ""
        binding.lineChartWeight.legend.isEnabled = true
        //binding.lineChart.description.textColor = ContextCompat.getColor(this, R.color.myOrange)
        binding.lineChartWeight.legend.textColor = ContextCompat.getColor(requireContext(), R.color.myDarkBlue)
        binding.lineChartWeight.legend.textSize = 18f
        //binding.lineChart.axisLeft.textColor = ContextCompat.getColor(this, R.color.myOrange)
        //binding.lineChart.xAxis.textColor = ContextCompat.getColor(this, R.color.myOrange)
        binding.lineChartWeight.setNoDataText("")
    }

    /**
     * Next method show weight Line Chart
     */

    private fun setupWorkoutDurationLineChart(statisticList: ArrayList<WorkoutStatisticModelClass>){
        val entriesDuration = ArrayList<Entry>()
        var counter = statisticList.size
        for (i in 0 until statisticList.size){
            if (counter <= 10){
                val totalDuration = statisticList[i].getExerciseDuration() + statisticList[i].getRestDuration()
                entriesDuration.add(Entry(i.toFloat(), totalDuration.toFloat()))
            }
            counter--
        }

        //customize data set
        val lineDataSetWeight = LineDataSet(entriesDuration, getString(R.string.st_workout_duration))
        lineDataSetWeight.mode = LineDataSet.Mode.CUBIC_BEZIER
        //values on peaks
        lineDataSetWeight.setDrawValues(true)
        lineDataSetWeight.valueTextColor = ContextCompat.getColor(requireContext(), R.color.myDarkBlue)
        lineDataSetWeight.valueTextSize = 14f


        lineDataSetWeight.setDrawCircles(true)
        lineDataSetWeight.setCircleColor(ContextCompat.getColor(requireContext(), R.color.myOrange))
        //line color and width
        lineDataSetWeight.color = ContextCompat.getColor(requireContext(), R.color.myOrange)
        lineDataSetWeight.lineWidth = 3f
        //color under line
        lineDataSetWeight.setDrawFilled(true)
        lineDataSetWeight.fillColor = ContextCompat.getColor(requireContext(), R.color.myGreenBlue)

        //add data to chart
        binding.lineChartWorkoutDuration.data = LineData(lineDataSetWeight)

        //make untouchable
        binding.lineChartWorkoutDuration.setTouchEnabled(false)
        binding.lineChartWorkoutDuration.isDragEnabled = true


        //hide all axis labels and grid background
        binding.lineChartWorkoutDuration.xAxis.isEnabled = false
        binding.lineChartWorkoutDuration.axisRight.isEnabled = false
        binding.lineChartWorkoutDuration.axisLeft.isEnabled = false
        binding.lineChartWorkoutDuration.xAxis.setDrawGridLines(false);
        binding.lineChartWorkoutDuration.axisLeft.setDrawGridLines(false);
        binding.lineChartWorkoutDuration.axisRight.setDrawGridLines(false);


        binding.lineChartWorkoutDuration.description.text = ""
        binding.lineChartWorkoutDuration.legend.isEnabled = true
        binding.lineChartWorkoutDuration.legend.textColor = ContextCompat.getColor(requireContext(), R.color.myDarkBlue)
        binding.lineChartWorkoutDuration.legend.textSize = 18f
        binding.lineChartWorkoutDuration.setNoDataText("")
    }


}