package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private lateinit var webView: WebView
    private lateinit var mAdView : AdView
    private val TAG = "testAD"
    private var count = 1

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
                mInterstitialAd = null
            }
        }


        //webView 게제
        webView = findViewById(R.id.webview)
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
        //agent 바꿔서 구글로그인 가능하게
        webView.settings.userAgentString = System.getProperty("http.agent")
        webView.loadUrl("https://chat.openai.com/auth/login")
        if(webView.url == "https://chat.openai.com/auth/login") {
            Toast.makeText(this, "ChatGPT Capacity full", Toast.LENGTH_SHORT).show()
        }

        //새로고침 버튼
        val btnRefresh = findViewById<ImageButton>(R.id.btn_refresh)
        btnRefresh.bringToFront()
        btnRefresh.setOnClickListener {
            webView.reload()
            Log.d("testURL", webView.url.toString())
        }

        //카테고리 버튼 클릭시
        val categoriButton = findViewById<Button>(R.id.btn_categori)

        categoriButton.setOnClickListener {
            //categori button 누를시 categori 액티비티 띄움
            startActivity(Intent(this, CategoryActivity::class.java))

            Toast.makeText(this, "The best effect when used in English", Toast.LENGTH_SHORT).show()
            count++
            //count 3 이상일때 버튼 누를시 광고 띄움
            if(count % 3 == 0){
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                } //광고를 보여줍니다.
                else {
                    Toast.makeText(applicationContext, "광고 로드 실패", Toast.LENGTH_SHORT)
                        .show()//광고를 불러오지 못했을때
                }
            }
        }

        val db = Firebase.firestore
        db.collection("catagori")
            .get()
            .addOnSuccessListener { result ->
                for(document in result) {
                    Log.d("fdTest", "${document.data}")
                }
            }


        webView.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
            }
        })




        //파이어베이스 등록
//        val btnDbUpdate = findViewById<Button>(R.id.dbUpdate)
//        val db = FirebaseFirestore.getInstance()

//        btnDbUpdate.setOnClickListener{
//            var map = mutableMapOf<String,Any>()
//
//            map["keyword"]="chatGPT"
//            var resultDTO = WordModel()
////            resultDTO.keyword ="food"
////            resultDTO.ensentence = listOf(
////                "asdascasc","asdascxzc","asdascwwww"
////            )
////            resultDTO.krsentence = listOf(
////                "asdascasc","asdascxzc","asdascwwww"
////            )
//
//            db.collection("catagori")
//                .document("4M1REAAhYLvhOZIVHDJh")
//                .update(map)
//
//        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }
        else{
            finish()
        }
    }




}