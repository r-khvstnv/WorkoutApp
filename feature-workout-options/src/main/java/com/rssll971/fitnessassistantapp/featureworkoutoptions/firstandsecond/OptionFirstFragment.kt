/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.core.utils.UtilsCore
import com.rssll971.fitnessassistantapp.featureworkoutoptions.R
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.FragmentOptionFirstBinding
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSComponentViewModel
import javax.inject.Inject

class OptionFirstFragment : BaseFragment() {
    private var _binding: FragmentOptionFirstBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<OptionsViewModel>(R.id.options_nested_graph){ viewModelFactory }

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
        _binding = FragmentOptionFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observes rest/exercise durations
        viewModel.restTime.observe(viewLifecycleOwner){
            binding.tvRestDurValue.text = UtilsCore.getFormattedTime(it * 1000L)
        }
        viewModel.exerciseTime.observe(viewLifecycleOwner){
            binding.tvExerciseDurValue.text = UtilsCore.getFormattedTime(it * 1000L)
        }


        with(binding){
            rBtnVoiceOn.setOnCheckedChangeListener { _, checked ->
                viewModel.setVoiceAvailability(checked)
            }
            ivRestDurDecrease.setOnClickListener {
                viewModel.decreaseRestTime()
            }
            ivRestDurIncrease.setOnClickListener {
                viewModel.increaseRestTime()
            }
            ivExerciseDurDecrease.setOnClickListener {
                viewModel.decreaseExerciseTime()
            }
            ivExerciseDurIncrease.setOnClickListener {
                viewModel.increaseExerciseTime()
            }


            /**Navigate to the next fragment (OptionSecondFragment)*/
            iBtnNext.setOnClickListener {
                findNavController().navigate(
                    R.id.action_options_first_fragment_to_options_second_fragment
                )
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}