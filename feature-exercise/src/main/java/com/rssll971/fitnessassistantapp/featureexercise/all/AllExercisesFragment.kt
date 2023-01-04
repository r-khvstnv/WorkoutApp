/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.featureexercise.all

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
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.featureexercise.R
import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAllExercisesBinding
import com.rssll971.fitnessassistantapp.featureexercise.di.FeatureExerciseComponentsViewModel
import javax.inject.Inject

internal class AllExercisesFragment : BaseFragment() {
    private var _binding: FragmentAllExercisesBinding? = null
    private val binding get() = _binding!!
    @Inject
    internal lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AllExercisesViewModel> { vmFactory }

    private lateinit var exercisesAdapter: AllExercisesAdapter

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<FeatureExerciseComponentsViewModel>()
            .allExercisesComponent
            .inject(this)
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

        setupRecyclerView()

        //Navigate to AddEditExerciseFragment onClick
        binding.ibAddNewExercise.setOnClickListener {
            findNavController().navigate(
                R.id.action_all_exercises_fragment_to_add_edit_exercise_fragment
            )
        }

        viewModel.allExercises.observe(viewLifecycleOwner){
            list ->
            list?.let {
                exercisesAdapter.submitList(it)
                if (list.isNotEmpty()){
                    binding.tvNoData.visibility = View.GONE
                } else{
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }
    }

    /**Method setting up exercise recycler view*/
    private fun setupRecyclerView(){
        exercisesAdapter = AllExercisesAdapter(
            context = requireContext(),
            onItemClicked = this::navigateToEditExerciseFragmentById,
            onItemDelete = viewModel::requestExerciseDeleting
        )

        binding.rvExercises.apply {
            adapter = exercisesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            setHasFixedSize(true)
        }
    }

    private fun navigateToEditExerciseFragmentById(id: Int){
        findNavController().navigate(
            AllExercisesFragmentDirections.actionAllExercisesFragmentToAddEditExerciseFragment(id)
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}