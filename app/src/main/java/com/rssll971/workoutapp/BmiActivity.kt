package com.rssll971.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.rssll971.workoutapp.databinding.ActivityBmiBinding
import kotlin.math.pow

class BmiActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityBmiBinding
    //metric system
    private var isMetricSystem: Boolean = true
    //check for estimation
    private var isCalculated: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        //measurement system
        binding.tbMeasurementSystem.setOnClickListener {
            isMetricSystem = binding.tbMeasurementSystem.isChecked
            showRightHints()
        }

        //calculate or exit
        /*
        binding.btnActions.setOnClickListener {
            if (!isCalculated){
                estimateBmi()
            }
            else{
                finish()
            }
        }

         */
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
        var weight: Float = binding.etWeight.text.toString().toFloat()
        var bmi: Float = 0.0f
        var bmiStatus: String = ""

        if (isMetricSystem){
            height /= 100.0f

            bmi = weight / (height.pow(2))
        }
        else{
            bmi = (weight / (height.pow(2))) * 703
        }

        //estimate bmi status
        bmiStatus = when(bmi){
            in 0.0f..18.5f -> "Недостаточно веса"
            in 18.6f..24.9f -> "В норме"
            in 25.0f..29.9f -> "Лишний вес"
            in 30.0f..34.9f -> "Ожирение"
            in 30.0f..34.9f -> "Сильное ожирение"
            else -> "Wrong DataType"
        }

        //show result
        showBmiResult(bmi, bmiStatus)
    }
    //show BMI
    private fun showBmiResult(bmi: Float, bmiStatus: String){
        isCalculated = true



        binding.tvBmiIndex.text = bmi.toInt().toString()
        binding.tvBmiStatus.text = bmiStatus
        //binding.btnActions.text = getString(R.string.st_finish)
    }
}