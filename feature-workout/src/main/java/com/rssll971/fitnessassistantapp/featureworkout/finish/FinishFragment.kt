package com.rssll971.fitnessassistantapp.featureworkout.finish

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rssll971.fitnessassistantapp.featureworkout.databinding.FragmentFinishBinding
import com.rssll971.fitnessassistantapp.core.R as RCore


class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Disable backPress
        requireActivity().onBackPressedDispatcher.addCallback(this){}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Navigate to OptionStartFragment onClick
        binding.btnQuit.setOnClickListener {
            val deepLink = Uri.parse(getString(RCore.string.deep_link_option_start_fragment))
            findNavController().navigate(deepLink)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}