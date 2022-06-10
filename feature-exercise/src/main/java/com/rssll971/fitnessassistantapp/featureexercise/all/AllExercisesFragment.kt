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
import com.rssll971.fitnessassistantapp.coredata.models.Exercise
import com.rssll971.fitnessassistantapp.featureexercise.R
import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAllExercisesBinding
import com.rssll971.fitnessassistantapp.featureexercise.utils.FeatureExerciseComponentsViewModel
import com.rssll971.fitnessassistantapp.featureexercise.utils.ItemCallback
import javax.inject.Inject

class AllExercisesFragment : BaseFragment() {
    private var _binding: FragmentAllExercisesBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AllExercisesViewModel> { vmFactory }

    private lateinit var exercisesAdapter: AllExercisesAdapter

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

        setupRecyclerView()

        //Navigate to AddEditExerciseFragment onClick
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_all_exercises_fragment_to_add_edit_exercise_fragment
            )
        }

        viewModel.allExercises.observe(viewLifecycleOwner){
            list ->
            list?.let {
                exercisesAdapter.updateList(list = list)
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
            requireContext(),
            object : ItemCallback{
                /**onClick -> Navigate to AddEditExerciseFragment with id as safeArgs*/
                override fun onClick(id: Int) {
                    findNavController().navigate(
                        AllExercisesFragmentDirections.actionAllExercisesFragmentToAddEditExerciseFragment(id)
                    )
                }
                override fun onDelete(exercise: Exercise) {
                    viewModel.requestExerciseDeleting(exercise = exercise)
                }
            })

        binding.rvExercises.apply {
            adapter = exercisesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false
            )
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}