package com.rssll971.workoutapp

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.rssll971.workoutapp.databinding.ActivityBmiBinding
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityBmiBinding
    //metric system
    private var isMetricSystem: Boolean = true
    //current dat
    private lateinit var currentDate: String
    //index
    private lateinit var bmiIndex: String
    //adapter
    private lateinit var bmiStatusAdapter: BmiResultStatusAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //hide result and history
        binding.llResult.visibility = View.INVISIBLE
        binding.llBmiHistory.visibility = View.GONE
        setupRecyclerView()

        //measurement system
        binding.tbMeasurementSystem.setOnClickListener {
            isMetricSystem = binding.tbMeasurementSystem.isChecked
            showRightHints()
        }

        //вычисляем bmi
        binding.llCalculate.setOnClickListener {
            if (binding.etHeight.text.isEmpty() && binding.etWeight.text.isEmpty()){
                //todo make as string
                Toast.makeText(this, "Add data", Toast.LENGTH_LONG).show()
            }
            else{
                //todo make as string
                Toast.makeText(this, "Added to History", Toast.LENGTH_LONG).show()
                estimateBmi()
            }
        }

        binding.clShowHistory.setOnClickListener {
            if (binding.ivExpand.rotation == 0F){
                showBmiHistory()
            }
            else{
                hideBmiHistory()
            }
        }
        binding.llDeleteHistory.setOnClickListener {
            deleteAllHistory()
            setupRecyclerView()
        }
    }


    //change text in editText hints
    private fun showRightHints(){
        if (isMetricSystem){
            binding.etHeight.hint = getString(R.string.st_height_m)
            binding.etWeight.hint = getString(R.string.st_weight_m)
        }
        else{
            binding.etHeight.hint = getString(R.string.st_height_us)
            binding.etWeight.hint = getString(R.string.st_weight_us)
        }
    }

    //estimate BMI
    private fun estimateBmi(){
        var height: Float = binding.etHeight.text.toString().toFloat()
        val weight: Float = binding.etWeight.text.toString().toFloat()
        var bmi = 0.0f
        var bmiStatus: String = ""

        if (isMetricSystem){
            height /= 100.0f

            bmi = weight / (height.pow(2))
        }
        else{
            bmi = (weight / (height.pow(2))) * 703
        }

        //todo change to string
        //estimate bmi status
        bmiStatus = when(bmi){
            in 0.0f..18.5f -> "Недостаточно веса"
            in 18.6f..24.9f -> "В норме"
            in 25.0f..29.9f -> "Лишний вес"
            in 30.0f..34.9f -> "Ожирение"
            in 30.0f..34.9f -> "Сильное ожирение"
            else -> "Экстримально Сильное ожирение"
        }

        //show result
        showBmiResult(bmi, bmiStatus)
    }


    //show BMI
    private fun showBmiResult(bmi: Float, bmiStatus: String){
        currentDate = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        bmiIndex = String.format("%.2f", bmi)

        binding.tvDate.text = currentDate
        binding.tvBmiIndex.text = "${resources.getString(R.string.st_bmi_index)}: $bmiIndex"
        binding.tvBmiStatus.text = "${resources.getString(R.string.st_description)}: $bmiStatus"
        //show result and history
        binding.llResult.visibility = View.VISIBLE
        addBmiRecord()
    }

    //add current bmi to database
    private fun addBmiRecord(){
        val weight = binding.etWeight.text.toString().toFloat()
        val height = binding.etHeight.text.toString().toFloat()

        //get database
        val dataBaseHandler = BmiDataBaseHandler(this)
        //note: system automatically change id
        val status = dataBaseHandler.addCurrentBmiResult(
            BmiHistoryModelClass(0, currentDate, weight, height, bmiIndex.toFloat()))
        if (status > -1){
            setupRecyclerView()
        }
    }

    //show history
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

    //rv with history
    private fun setupRecyclerView(){
            binding.recyclerViewBmi.layoutManager = LinearLayoutManager(this)
            bmiStatusAdapter = BmiResultStatusAdapter(this, getItemBmiHistoryList())
            binding.recyclerViewBmi.adapter = bmiStatusAdapter
    }

    /**
     * Next method get item data for database
     */
    private fun getItemBmiHistoryList() : ArrayList<BmiHistoryModelClass>{
        val dataBaseHandler = BmiDataBaseHandler(this)
        val bhmList: ArrayList<BmiHistoryModelClass> = dataBaseHandler.viewBmiResult()
        return bhmList
    }

    private fun deleteAllHistory(){
        val dataBaseHandler = BmiDataBaseHandler(this)
        dataBaseHandler.eraseAll()
    }

}