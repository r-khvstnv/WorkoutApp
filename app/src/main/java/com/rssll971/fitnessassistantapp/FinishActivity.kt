package com.rssll971.fitnessassistantapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.rssll971.fitnessassistantapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding
    private lateinit var adViewBannerFinish: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Create view using Binding**/
        binding = ActivityFinishBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /** Ads*/
        MobileAds.initialize(this)
        adViewBannerFinish = findViewById(R.id.adView_banner_finish_bottom)
        adViewBannerFinish.loadAd(AdRequest.Builder().build())

        adViewBannerFinish.adListener = object : AdListener(){
            override fun onAdClosed() {
                adViewBannerFinish.loadAd(AdRequest.Builder().build())
            }
        }

        binding.llTick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}