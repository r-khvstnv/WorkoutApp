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
import com.rssll971.fitnessassistantapp.featureexercise.R
import com.rssll971.fitnessassistantapp.featureexercise.all.di.AllComponentViewModel
import com.rssll971.fitnessassistantapp.featureexercise.databinding.FragmentAllBinding
import javax.inject.Inject

class AllFragment : Fragment() {
    private var _binding: FragmentAllBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AllViewModel> { vmFactory }

    override fun onAttach(context: Context) {
        ViewModelProvider(this).get<AllComponentViewModel>().allComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.testText.observe(viewLifecycleOwner) { text ->
            text?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}