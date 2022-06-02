package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.FragmentOptionSecondBinding
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSComponentViewModel
import javax.inject.Inject


class OptionSecondFragment : Fragment() {
    private var _binding: FragmentOptionSecondBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by activityViewModels<OptionsViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<OptionsFSComponentViewModel>()
            .optionsFSComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}