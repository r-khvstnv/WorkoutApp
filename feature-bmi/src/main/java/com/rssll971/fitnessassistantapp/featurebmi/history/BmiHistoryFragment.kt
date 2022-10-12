/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featurebmi.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.color.MaterialColors
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.featurebmi.R
import com.rssll971.fitnessassistantapp.featurebmi.databinding.FragmentBmiHistoryBinding
import com.rssll971.fitnessassistantapp.featurebmi.di.FeatureBmiComponentsViewModel
import com.rssll971.fitnessassistantapp.featurebmi.utils.Utils
import javax.inject.Inject

internal class BmiHistoryFragment : BaseFragment() {
    private var _binding: FragmentBmiHistoryBinding? = null
    private val binding get() = _binding!!
    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<BmiHistoryViewModel> { viewModelFactory }

    private lateinit var adapterBmi: BmiHistoryAdapter

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<FeatureBmiComponentsViewModel>()
            .bmiHistoryComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupChartAppearance()

        //Navigate to BmiCalculationFragment onClick
        binding.fabNewBmi.setOnClickListener {
            findNavController().navigate(
                R.id.action_bmi_history_fragment_to_bmi_calculation_fragment
            )
        }
        //Request to clear bmi table onClick
        binding.btnClearHistory.setOnClickListener {
            viewModel.deleteAllBmi()
        }

        viewModel.bmiList.observe(viewLifecycleOwner){
            list ->
            list?.let {
                adapterBmi.updateList(it.reversed())

                if (it.isNotEmpty()){
                    binding.clBmi.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.GONE
                } else{
                    binding.clBmi.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }

        viewModel.bmiBarEntries.observe(viewLifecycleOwner){
            entryList ->
            entryList?.let {
                updateChartData(it)
            }
        }
    }

    private fun setupRecyclerView(){
        adapterBmi = BmiHistoryAdapter(requireContext())
        binding.included.rvBmiHistory.apply {
            adapter = adapterBmi
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    /**Method setting up LineChart appearance*/
    private fun setupChartAppearance(){
        binding.bmiBarChartVertical.apply {
            description.isEnabled = false
            setDrawGridBackground(false)
            isDragEnabled = true
            legend.isEnabled = false

            axisRight.isEnabled = false
            axisRight.setDrawGridLines(false)

            axisLeft.setDrawGridLines(false)
            axisLeft.textColor = MaterialColors.getColor(this, R.attr.colorOnBackground)

            xAxis.apply {
                setDrawLabels(true)
                setDrawGridLines(false)
                textColor = MaterialColors.getColor(binding.bmiBarChartVertical, R.attr.colorOnBackground)
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = -90f
                granularity = 1f
            }

            /**Instead of x value, show date from associated list*/
            xAxis.valueFormatter = object : ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return viewModel.getDateByIndex(value.toInt())
                }
            }
        }

    }

    /**Method updates data in corresponding LineCharts.
     * Also will be implemented some UI style for dataSet*/
    private fun updateChartData(barEntryList: List<BarEntry>){
        val dataSet = BarDataSet(barEntryList, "")
        val colorList = arrayListOf<Int>()
        //Add corresponding color to bmiIndex
        for (entry in barEntryList){
            colorList.add(Utils.getBmiIndexStatusColor(entry.y, requireContext()))
        }
        dataSet.colors = colorList

        dataSet.setDrawValues(false)
        val barData = BarData(dataSet)

        binding.bmiBarChartVertical.apply {
            data = barData
            setVisibleXRangeMaximum(12f)
            moveViewToX(barEntryList.size - 1f)
            animateY(1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}