package com.rssll971.fitnessassistantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.fitnessassistantapp.databinding.FragmentBmiBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow


class BmiFragment : Fragment() {
    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    //metric system
    private var isMetricSystem: Boolean = true
    //current date
    private lateinit var currentDate: String
    //adapter for bmi history
    private lateinit var bmiStatusAdapter: BmiResultStatusAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** Show hints*/
        isMetricSystem = binding.tbMeasurementSystem.isChecked
        showRightHints()

        /** Hide result and history */
        binding.llResult.visibility = View.INVISIBLE
        binding.llBmiHistory.visibility = View.GONE
        setupRecyclerView()


        //measurement system
        binding.tbMeasurementSystem.setOnClickListener {
            isMetricSystem = binding.tbMeasurementSystem.isChecked
            showRightHints()
        }
        //bmi estimation
        binding.llCalculate.setOnClickListener {
            if (binding.etHeight.text.isEmpty() && binding.etWeight.text.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.st_add_data), Toast.LENGTH_LONG).show()
            }
            else{
                estimateBmi()
            }
        }
        //show history
        binding.clShowHistory.setOnClickListener {
            if (binding.ivExpand.rotation == 0F){
                showBmiHistory()
            }
            else{
                hideBmiHistory()
            }
        }
        //delete history
        binding.llDeleteHistory.setOnClickListener {
            deleteAllHistory()
            setupRecyclerView()
        }
    }



    /**
     * Show right hints depending on measurement system
     */
    private fun showRightHints(){
        if (isMetricSystem){
            binding.etHeight.hint = getString(R.string.st_height) + getString(R.string.st_sm)
            binding.etWeight.hint = getString(R.string.st_weight) + getString(R.string.st_kg)
        }
        else{
            binding.etHeight.hint = getString(R.string.st_height) + getString(R.string.st_in)
            binding.etWeight.hint = getString(R.string.st_weight) + getString(R.string.st_lbs)
        }
    }

    /**
     * Next method estimate BMI index
     */
    private fun estimateBmi(){
        var height: Float = binding.etHeight.text.toString().toFloat()
        val weight: Float = binding.etWeight.text.toString().toFloat()
        val bmi: Float

        if (isMetricSystem){
            height /= 100.0f
            bmi = weight / (height.pow(2))
        }
        else{
            bmi = (weight / (height.pow(2))) * 703
        }


        /** Get right status for bmi index */
        val bmiStatus: String = when(bmi){
            in 0.0f..18.5f -> getString(R.string.st_underweight)
            in 18.6f..24.9f -> getString(R.string.st_normal)
            in 25.0f..29.9f -> getString(R.string.st_overweight)
            in 30.0f..34.9f -> getString(R.string.st_obese)
            in 30.0f..34.9f -> getString(R.string.st_ex_obese)
            else -> getString(R.string.st_ex_obese)
        }
        //show result
        showBmiResult(bmi, bmiStatus)
    }


    /**
     * Next method show bmi result in prepared card
     */
    private fun showBmiResult(bmi: Float, bmiStatus: String){
        currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        val bmiIndex = String.format("%.2f", bmi)

        binding.tvDate.text = currentDate
        binding.tvBmiIndex.text = "${resources.getString(R.string.st_bmi_index)}: $bmiIndex"
        binding.tvBmiStatus.text = "${resources.getString(R.string.st_status)}: $bmiStatus"
        //show result and history
        binding.llResult.visibility = View.VISIBLE
        //add in db
        addBmiRecord(bmi)
    }


    /**
     * Next methods show or hide bmi history and smooth scroll layout
     */
    private fun showBmiHistory(){
        binding.llBmiHistory.visibility = View.VISIBLE
        binding.ivExpand.animate().rotation(-180F)
        binding.svBmi.requestChildFocus(binding.llDeleteHistory, binding.llDeleteHistory)
    }
    //hide history
    private fun hideBmiHistory(){
        binding.llBmiHistory.visibility = View.GONE
        binding.ivExpand.animate().rotation(0F)
        binding.svBmi.smoothScrollTo(binding.clShowHistory.bottom, binding.clShowHistory.bottom)
    }


    /**
     * Next method run RecyclerView with bmi history
     */
    private fun setupRecyclerView(){
        binding.recyclerViewBmi.layoutManager = LinearLayoutManager(requireContext())
        val bmiHistoryList = getItemBmiHistoryList()
        bmiStatusAdapter = BmiResultStatusAdapter(requireContext(), bmiHistoryList)
        binding.recyclerViewBmi.adapter = bmiStatusAdapter
    }


    /**
     * Next method add current bmi estimation in database
     */
    private fun addBmiRecord(bmi: Float){
        val weight = binding.etWeight.text.toString().toFloat()
        val height = binding.etHeight.text.toString().toFloat()

        //get database
        val dataBaseHandler = BmiDataBaseHandler(requireContext())
        //note: system automatically change id
        val status = dataBaseHandler.addCurrentBmiResult(
            BmiHistoryModelClass(0, currentDate, weight, height, bmi))
        if (status > -1){
            setupRecyclerView()
            Toast.makeText(requireContext(), getString(R.string.st_added_to_history), Toast.LENGTH_LONG).show()
        }
    }
    /**
     * Next method get all bmi history from database
     */
    private fun getItemBmiHistoryList() : ArrayList<BmiHistoryModelClass>{
        val dataBaseHandler = BmiDataBaseHandler(requireContext())
        return dataBaseHandler.viewBmiResult()
    }
    /**
     * Next method delete all bmi history from database
     */
    private fun deleteAllHistory(){
        val dataBaseHandler = BmiDataBaseHandler(requireContext())
        dataBaseHandler.eraseAll()
    }


}