package com.rssll971.workoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.rssll971.workoutapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Create view using Binding**/
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //start exercises
        binding.llStart.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }


        //bmi calculator
        binding.llBmi.setOnClickListener {
            val intent = Intent(this, BmiActivity::class.java)
            startActivity(intent)
        }

        //history
        binding.llActivities.setOnClickListener {
            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
        }

        //history
        binding.llSettings.setOnClickListener {
            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
        }


    }
}