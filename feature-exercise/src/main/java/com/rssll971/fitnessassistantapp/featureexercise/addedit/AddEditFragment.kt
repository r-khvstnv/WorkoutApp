package com.rssll971.fitnessassistantapp.featureexercise.addedit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rssll971.fitnessassistantapp.featureexercise.R

class AddEditFragment : Fragment() {

    companion object {
        fun newInstance() = AddEditFragment()
    }

    private lateinit var viewModel: AddEditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEditViewModel::class.java)
        // TODO: Use the ViewModel
    }

}