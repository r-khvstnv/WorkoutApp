package com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.fitnessassistantapp.core.base.BaseFragment
import com.rssll971.fitnessassistantapp.featureworkoutoptions.R
import com.rssll971.fitnessassistantapp.featureworkoutoptions.databinding.FragmentOptionSecondBinding
import com.rssll971.fitnessassistantapp.featureworkoutoptions.firstandsecond.di.OptionsFSComponentViewModel
import javax.inject.Inject


class OptionSecondFragment : BaseFragment() {
    private var _binding: FragmentOptionSecondBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by navGraphViewModels<OptionsViewModel>(R.id.options_nested_graph){ viewModelFactory }

    private lateinit var selectableExercisesAdapter: SelectableExercisesAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.exerciseList.observe(viewLifecycleOwner){
            list ->
            list?.let {
                if (it.isNotEmpty()){
                    selectableExercisesAdapter.updateExerciseList(it.reversed())
                    binding.iBtnStart.visibility = View.VISIBLE
                } else{
                    binding.iBtnStart.visibility = View.GONE
                }
            }
        }
        viewModel.isStatisticAdded.observe(viewLifecycleOwner){
            added ->
            if (added){
                val deepLink = Uri.parse("myApp://workoutFragment")
                findNavController().navigate(deepLink)
            }
        }




        binding.iBtnStart.setOnClickListener {
            val list = selectableExercisesAdapter.getSelectedExercisesIdList()
            if (list.isNotEmpty()){
                viewModel.setupStatistic(list = list, System.currentTimeMillis())
            } else{
                showSnackBarMessage(getString(R.string.error_no_exercise_selected))
            }
        }
    }

    private fun setupRecyclerView(){
        selectableExercisesAdapter = SelectableExercisesAdapter(requireContext())
        binding.rvExercises.apply {
            adapter = selectableExercisesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}