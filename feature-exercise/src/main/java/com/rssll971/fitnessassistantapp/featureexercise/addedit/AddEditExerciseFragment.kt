package com.rssll971.fitnessassistantapp.featureexercise.addedit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAddEditExercisesBinding
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExerciseComponentsViewModel
import javax.inject.Inject

class AddEditExerciseFragment : Fragment() {
    private var _binding: FragmentAddEditExercisesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AddEditExerciseViewModel> { vmFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<FeatureExerciseComponentsViewModel>().addEditExerciseComponent.inject(this)
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.textTest.observe(viewLifecycleOwner){
            string ->
            string?.let {
                Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}