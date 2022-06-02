package com.rssll971.fitnessassistantapp.featurebmi.calculation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.featurebmi.R
import com.rssll971.fitnessassistantapp.featurebmi.databinding.FragmentBmiCalculationBinding
import com.rssll971.fitnessassistantapp.featurebmi.deps.FeatureBmiComponentsViewModel
import com.rssll971.fitnessassistantapp.featurebmi.utils.Utils
import javax.inject.Inject
import com.rssll971.fitnessassistantapp.core.R as RCore

class BmiCalculationFragment : BaseFragment() {
    private var _binding: FragmentBmiCalculationBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<BmiCalculationViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<FeatureBmiComponentsViewModel>()
            .bmiCalculationComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiCalculationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculate.setOnClickListener {
            calculateBmi()
        }

        viewModel.bmi.observe(viewLifecycleOwner){
            bmi ->
            bmi?.let {
                with(binding){
                    resultCard.visibility = View.VISIBLE
                    tvBmiIndex.text = getString(R.string.title_bmi_index) + ": " + Utils.getRoundedFloat(it.bmiIndex)
                    tvBmiStatus.text = getString(R.string.title_status) + ": " + Utils.getBmiIndexStatus(it.bmiIndex, requireContext())
                }
            }
        }
    }



    /**Method firstly request error resetting and after
     * will check that fields are not empty.
     * Otherwise show error message in corresponding field*/
    private fun isUserInputIsValid(): Boolean{
        var result = false
        val error: String = getString(RCore.string.error_empty_field)

        resetFieldsErrors()

        with(binding){
            when{
                TextUtils.isEmpty(etHeight.text) -> etHeight.error = error
                TextUtils.isEmpty(etWeight.text) -> etWeight.error = error
                else -> result = true
            }
        }

        return result
    }

    /**Next method reset errors in all available fields*/
    private fun resetFieldsErrors(){
        with(binding){
            etHeight.error = null
            etWeight.error = null
        }
    }

    private fun calculateBmi(){
        if (isUserInputIsValid()){
            viewModel.calculateBmi(
                System.currentTimeMillis(),
                binding.etHeight.text.toString().toFloat(),
                binding.etWeight.text.toString().toFloat()
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}