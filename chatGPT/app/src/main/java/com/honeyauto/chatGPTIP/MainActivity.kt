package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver.OnScrollChangedListener
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback


class MainActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    lateinit var webview: WebView
    lateinit var mAdView : AdView
    private val TAG = "testAD"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //광고
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.d(TAG, p0.toString())
                mInterstitialAd = null
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                Log.d(TAG, "AD was loaded.")
                mInterstitialAd = p0
            }
        })

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d("TAG", "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                Log.d("TAG", "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("TAG", "Ad showed fullscreen content.")
                mInterstitialAd = null;
            }
        }

        webview = findViewById(R.id.webview)
        webview.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }

        webview.settings.userAgentString = System.getProperty("http.agent")
        webview.loadUrl("https://chat.openai.com/auth/login")

        val catgoriButton = findViewById<Button>(R.id.btn_catagori)

        catgoriButton.setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
            Toast.makeText(this, "The best effect when used in English", Toast.LENGTH_SHORT).show()
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)} //광고를 보여줍니다.
            else {
                Toast.makeText(applicationContext,"광고 로드 실패", Toast.LENGTH_SHORT).show()//광고를 불러오지 못했을때
            }
        }

    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack()
        }
        else{
            finish()
        }
    }


}