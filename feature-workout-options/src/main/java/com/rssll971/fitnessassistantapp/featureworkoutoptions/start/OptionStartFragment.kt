package com.rssll971.fitnessassistantapp.featureworkoutoptions.start

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.color.MaterialColors
import com.rssll971.fitnessassistantapp.core.utils.ConstantsCore
import com.rssll971.fitnessassistantapp.core.utils.UtilsCore
import com.rssll971.fitnessassistantapp.featureworkoutoptions.R
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.FragmentOptionStartBinding
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartComponentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OptionStartFragment : Fragment() {
    private var _binding: FragmentOptionStartBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<OptionStartViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<OptionStartComponentViewModel>()
            .optionStartComponent.inject(this)
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(this){}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            lineChartWorkoutDuration.setupChartAppearance()
            lineChartWorkoutAmount.setupChartAppearance()

            llStart.setOnClickListener {
                findNavController().navigate(
                    R.id.options_nested_graph
                )
            }
            ivInfo.setOnClickListener {
                val deepLink = Uri.parse(getString(R.string.deep_link_info_fragment))
                findNavController().navigate(deepLink)
            }
        }

        viewModel.isLineChartShouldBeShown.observe(viewLifecycleOwner){
            isShouldBeVisible ->
            if (isShouldBeVisible){
                binding.llStatistic.visibility = View.VISIBLE
                binding.tvNoData.visibility = View.GONE
            } else{
                binding.llStatistic.visibility = View.GONE
                binding.tvNoData.visibility = View.VISIBLE
            }
        }

        viewModel.workoutDurationEntries.observe(viewLifecycleOwner){
            list ->
            list?.let {
                if (it.isNotEmpty()){
                    updateDurationLineChart(it, binding.lineChartWorkoutDuration)
                }
            }
        }

        viewModel.workoutExerciseAmountEntries.observe(viewLifecycleOwner){
                list ->
            list?.let {
                if (it.isNotEmpty()){
                    updateExerciseAmountLineChart(it, binding.lineChartWorkoutAmount)
                }
            }
        }
    }


    private fun LineChart.setupChartAppearance(){
        setTouchEnabled(false)
        description.isEnabled = true
        setDrawGridBackground(false)
        isDragEnabled = true
        legend.isEnabled = false
        description.textColor = MaterialColors.getColor(this@setupChartAppearance, R.attr.colorOnBackground)
        description.textSize = 18f

        axisRight.isEnabled = false
        axisRight.setDrawGridLines(false)

        axisLeft.setDrawGridLines(false)
        axisLeft.isEnabled = false

        xAxis.apply {
            setDrawLabels(true)
            setDrawGridLines(false)
            textColor = MaterialColors.getColor(this@setupChartAppearance, R.attr.colorOnBackground)
            position = XAxis.XAxisPosition.BOTTOM
            labelRotationAngle = -90f
            granularity = 1f
        }

        xAxis.valueFormatter = object : ValueFormatter(){
            override fun getFormattedValue(value: Float): String {
                return viewModel.getDateByIndex(value.toInt())
            }
        }
    }

    private fun updateDurationLineChart(entryList: List<Entry>, lineChart: LineChart){
        val title = getString(R.string.title_workout_duration)
        val durationDataSet = LineDataSet(entryList, title)
        durationDataSet.apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawValues(true)
            valueTextColor = MaterialColors.getColor(lineChart, R.attr.colorPrimary)
            valueTextSize = 12f
            setDrawCircles(true)
            setCircleColor(MaterialColors.getColor(lineChart, R.attr.colorPrimary))
            color = MaterialColors.getColor(lineChart, R.attr.colorPrimary)
            lineWidth = 3f
            setDrawFilled(true)
            fillColor = MaterialColors.getColor(lineChart, R.attr.colorOutline)

            valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return UtilsCore.getFormattedTime(value.toInt() * 1000L)
                }
            }
        }
        val chartData = LineData(durationDataSet)
        lineChart.apply {
            data = chartData
            description.text = title
            animateX(1000)
            invalidate()
        }
    }
    private fun updateExerciseAmountLineChart(entryList: List<Entry>, lineChart: LineChart){
        val title = getString(R.string.title_exercise_amount)
        val amountDataSet = LineDataSet(entryList, title)
        amountDataSet.apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawValues(true)
            valueTextColor = MaterialColors.getColor(lineChart, R.attr.colorSecondary)
            valueTextSize = 12f
            setDrawCircles(true)
            setCircleColor(MaterialColors.getColor(lineChart, R.attr.colorSecondary))
            color = MaterialColors.getColor(lineChart, R.attr.colorSecondary)
            lineWidth = 3f
            setDrawFilled(true)
            fillColor = MaterialColors.getColor(lineChart, R.attr.colorOutline)

            valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
        }
        val chartData = LineData(amountDataSet)
        lineChart.apply {
            data = chartData
            description.text = title
            animateX(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}