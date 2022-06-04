package com.rssll971.fitnessassistantapp.featureworkoutoptions.start

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.rssll971.fitnessassistantapp.featureworkoutoptions.R
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.FragmentOptionStartBinding
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartComponent
import com.rssll971.fitnessassistantapp.featureworkoutoptions.start.di.OptionStartComponentViewModel
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

        binding.llStart.setOnClickListener {
            findNavController().navigate(
                R.id.options_nested_graph
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}