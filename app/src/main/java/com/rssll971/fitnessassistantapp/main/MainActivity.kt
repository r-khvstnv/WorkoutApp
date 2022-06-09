package com.rssll971.fitnessassistantapp.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.WorkoutApplication
import com.rssll971.fitnessassistantapp.core.utils.ConstantsCore
import com.rssll971.fitnessassistantapp.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WorkoutApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MobileAds.initialize(this)
        val adRequest = AdRequest.Builder().build()
        binding.adViewMain.loadAd(adRequest)

        binding.adViewMain.adListener = object : AdListener(){
            override fun onAdClosed() {
                val request = AdRequest.Builder().build()
                binding.adViewMain.loadAd(request)
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.e("Ads", "${p0.code}: ${p0.message}")
                val request = AdRequest.Builder().build()
                binding.adViewMain.loadAd(request)
            }
        }

        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.bmi_history_fragment -> showNavAndAdView()
                R.id.options_start_fragment -> showNavAndAdView()
                R.id.all_exercises_fragment -> showNavAndAdView()
                R.id.info_fragment -> showNavAndAdView()
                else -> hideNavAndAdView()
            }
        }


        val sharedPref = getSharedPreferences(ConstantsCore.WORKOUT_APP_SHARED_PREF, Context.MODE_PRIVATE)
        val isFirstLaunch: Boolean = sharedPref.getBoolean(ConstantsCore.IS_FIRS_APP_LAUNCH, true)
        if (isFirstLaunch){
            viewModel.insertDefaultExercises(sharedPref)
        }
    }



    private fun hideNavAndAdView(){
        binding.navView.visibility = View.GONE
        binding.adViewMain.visibility = View.GONE
    }
    private fun showNavAndAdView(){
        binding.navView.visibility = View.VISIBLE
        binding.adViewMain.visibility = View.VISIBLE
    }
}