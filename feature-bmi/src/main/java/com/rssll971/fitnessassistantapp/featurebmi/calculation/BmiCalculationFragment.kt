package com.rssll971.fitnessassistantapp.featurebmi.calculation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.rssll971.fitnessassistantapp.featurebmi.databinding.FragmentBmiCalculationBinding
import com.rssll971.fitnessassistantapp.featurebmi.deps.FeatureBmiComponentsViewModel
import javax.inject.Inject

class BmiCalculationFragment : Fragment() {
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}