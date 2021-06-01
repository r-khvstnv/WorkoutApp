package com.rssll971.fitnessassistantapp.activities

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.*
import com.rssll971.fitnessassistantapp.databinding.ActivityMainBinding
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.rssll971.fitnessassistantapp.R
import com.rssll971.fitnessassistantapp.fragments.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //main ad banner
    private lateinit var adViewBannerMain: AdView
    //interstitial ad
    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    /**
     * Fullscreen Mode
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    )
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Create view using Binding**/
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** Next line hides redundant space under nav menu, which was created by itself*/
        binding.bnvMenu.setOnApplyWindowInsetsListener(null)

        /** Firebase*/
        firebaseAnalytics = Firebase.analytics



        /** Ads*/
        prepareAds()

        /**
         * Fragments
         */
        val infoFragment = InfoFragment()
        val exerciseCatalogFragment = ExerciseCatalogFragment()
        val bmiFragment = BmiFragment()
        val startWorkout = StartWorkoutFragment()
        val statisticFragment = StatisticFragment()
        binding.bnvMenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.m_info -> {
                    showInterstitialAd()
                    makeAsCurrentFragment(infoFragment)
                }
                R.id.m_activities -> {
                    showInterstitialAd()
                    makeAsCurrentFragment(exerciseCatalogFragment)
                }
                R.id.m_start -> makeAsCurrentFragment(startWorkout)
                R.id.m_bmi -> makeAsCurrentFragment(bmiFragment)
                R.id.m_statistics -> makeAsCurrentFragment(statisticFragment)
            }
            true
        }
        /** Show default fragment*/
        if (savedInstanceState == null){
            binding.bnvMenu.selectedItemId = R.id.m_start
        }
    }

    /**
     * Next three methods provide operations relative for all Ads in MainActivity
     */
    private fun prepareAds(){
        //interstitial
        requestInterstitialAd()
        //banner
        MobileAds.initialize(this)
        adViewBannerMain = findViewById(R.id.adView_banner_main)
        adViewBannerMain.loadAd(AdRequest.Builder().build())

        adViewBannerMain.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerMain.loadAd(AdRequest.Builder().build())
            }
        }
    }
    private fun requestInterstitialAd(){
        val adRequest = AdRequest.Builder().build()
        loadInterstitialAd(adRequest)
    }
    private fun loadInterstitialAd(adRequest: AdRequest){
        InterstitialAd.load(this, getString(R.string.st_interstitial_ad_id),
            adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("AdMob", adError.message)
                mInterstitialAd = null
            }
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd

                mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        requestInterstitialAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                        requestInterstitialAd()
                    }

                    override fun onAdShowedFullScreenContent() {
                        mInterstitialAd = null
                    }
                }
            }
        })
    }
    private fun showInterstitialAd(){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        }
    }


    /**
     * Next method hide keyboard on outside touch
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null){
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Next method load corresponding fragment required for selected item in menu
     */
    private fun makeAsCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_container, fragment)
            commit()
    }
}