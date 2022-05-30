package com.rssll971.fitnessassistantapp.featureexercise.addedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAddEditExercisesBinding

class AddEditExerciseFragment : Fragment() {
    private var _binding: FragmentAddEditExercisesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditExercisesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}