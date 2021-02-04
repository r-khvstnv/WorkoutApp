package com.rssll971.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rssll971.workoutapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}