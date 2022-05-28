package com.rssll971.fitnessassistantapp.featurebmi

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class BmiFragment : Fragment() {

    companion object {
        fun newInstance() = BmiFragment()
    }

    private lateinit var viewModel: BmiViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bmi, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BmiViewModel::class.java)
        // TODO: Use the ViewModel
    }

}