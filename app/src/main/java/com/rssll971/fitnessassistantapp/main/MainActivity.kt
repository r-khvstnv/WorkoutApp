/************************************************
 * Created by Ruslan Khvastunov                 *
 * r.khvastunov@gmail.com                       *
 * Copyright (c) 2022                           *
 * All rights reserved.                         *
 *                                              *
 ************************************************/

package com.rssll971.fitnessassistantapp.main

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
import com.rssll971.fitnessassistantapp.di.main.DaggerMainComponent
import javax.inject.Inject

internal class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        injector()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        /**Ads*/
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


        /**NavigationView*/
        val navView: BottomNavigationView = binding.navView
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.findNavController()
        navView.setupWithNavController(navController)


        /**
         * NavigationView/AdView are visible only for Fragments represented in bottom menu
         * */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){
                R.id.bmi_history_fragment -> showNavAndAdView()
                R.id.options_start_fragment -> showNavAndAdView()
                R.id.all_exercises_fragment -> showNavAndAdView()
                R.id.info_fragment -> showNavAndAdView()
                else -> hideNavAndAdView()
            }
        }

        /**
         * SharedPreference stores the Boolean value of firstLaunch.
         * Accordingly, defaultExercises should be added to the database only on first app launch
         * */
        val sharedPref = getSharedPreferences(ConstantsCore.WORKOUT_APP_SHARED_PREF, Context.MODE_PRIVATE)
        val isFirstLaunch: Boolean = sharedPref.getBoolean(ConstantsCore.IS_FIRS_APP_LAUNCH, true)
        if (isFirstLaunch){
            viewModel.insertDefaultExercises(sharedPref)
        }
    }

    /**
     * Methods handle NavigationView/AdView visibility
     * */
    private fun hideNavAndAdView(){
        binding.navView.visibility = View.GONE
        binding.adViewMain.visibility = View.GONE
    }
    private fun showNavAndAdView(){
        binding.navView.visibility = View.VISIBLE
        binding.adViewMain.visibility = View.VISIBLE
    }

    private fun injector(){
        val mainComponent = DaggerMainComponent
            .builder()
            .appComponent((applicationContext as WorkoutApplication).appComponent)
            .build()
        mainComponent.inject(this)
    }
}