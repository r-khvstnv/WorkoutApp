package com.rssll971.fitnessassistantapp.featureexercise.all

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.rssll971.fitnessassistantapp.featureexercise.R
import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAllExercisesBinding
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExerciseComponentsViewModel
import javax.inject.Inject

class AllExercisesFragment : Fragment() {
    private var _binding: FragmentAllExercisesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AllExercisesViewModel> { vmFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<FeatureExerciseComponentsViewModel>().allExercisesComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isAdded.observe(viewLifecycleOwner){
            success ->
            if (success){
                Toast.makeText(requireContext(), success.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.allEx.observe(viewLifecycleOwner){
            list ->
            list?.let {
                binding.tvNoData.text = list.toString()
            }
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                R.id.add_edit_exercise_fragment
            )
        }
    }
}