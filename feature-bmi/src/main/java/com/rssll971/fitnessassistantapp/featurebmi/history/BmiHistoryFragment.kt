package com.rssll971.fitnessassistantapp.featurebmi.history

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.fitnessassistantapp.featurebmi.R
import com.rssll971.fitnessassistantapp.featurebmi.databinding.FragmentBmiHistoryBinding
import com.rssll971.fitnessassistantapp.featurebmi.utils.FeatureBmiComponentsViewModel
import javax.inject.Inject

class BmiHistoryFragment : Fragment() {
    private var _binding: FragmentBmiHistoryBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<BmiHistoryViewModel> { viewModelFactory }

    private lateinit var adapterBmi: BmiHistoryAdapter

    override fun onAttach(context: Context) {
        ViewModelProvider(this)
            .get<FeatureBmiComponentsViewModel>()
            .bmiHistoryComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBmiHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.fabNewBmi.setOnClickListener {
            findNavController().navigate(R.id.bmi_calculation_fragment)
        }
        binding.btnClearHistory.setOnClickListener {
            viewModel.deleteAllBmi()
        }

        viewModel.bmiList.observe(viewLifecycleOwner){
            list ->
            list.let {
                adapterBmi.updateList(it.reversed())
                if (it.isNotEmpty()){
                    binding.clBmi.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.GONE
                } else{
                    binding.clBmi.visibility = View.GONE
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupRecyclerView(){
        adapterBmi = BmiHistoryAdapter(requireContext())
        binding.included.rvBmiHistory.apply {
            adapter = adapterBmi
            layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}