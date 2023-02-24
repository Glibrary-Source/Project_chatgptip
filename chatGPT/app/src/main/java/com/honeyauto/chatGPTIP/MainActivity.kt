package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.honeyauto.chatGPTIP.databinding.ActivityCategoryBinding
import com.honeyauto.chatGPTIP.databinding.ActivityMainBinding
import com.honeyauto.chatGPTIP.model.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "testAD"
    private var count = 1
    private val db = Firebase.firestore

    private lateinit var webView: WebView
    private lateinit var mAdView: AdView
    private lateinit var binding: ActivityMainBinding


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)


        //언어선택 관련
        db.collection("languagelist")
            .get()
            .addOnSuccessListener { result ->
                val categoriList = ArrayList<LanguageWordModel>()
                for (snapshot in result.documents) {
                    val item = snapshot.toObject(LanguageWordModel::class.java)
                    categoriList.add(item!!)
                    MyGlobals.instance!!.languageList = categoriList[0].languagelist
                    MyGlobals.instance!!.buttontext = categoriList[0].buttontext
                }
            }

        //카테고리 불러오기
        db.collection("detailcategory").document("detailcategorylist")
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    MyGlobals.instance!!.detailModel = it.result.toObject(DetailWordModel::class.java)
                }
            }

        //광고
        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    Log.d(TAG, p0.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    Log.d(TAG, "AD was loaded.")
                    mInterstitialAd = p0
                }
            })

        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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

        //해당 국가 지역 코드 얻기위해서
        val locale: Locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            applicationContext.resources.configuration.locales[0]
        } else {
            applicationContext.resources.configuration.locale
        }
        //agent 바꿔서 구글로그인 가능하게
        webView.settings.userAgentString = System.getProperty("http.agent")
        webView.loadUrl("https://chat.openai.com/chat")
        //만약 챗 gpt 서버 수용량이 가득 찼거나 로그인이 필요할 떄 토스트 메시지 띄움
        if (webView.url == "https://chat.openai.com/auth/login") {
            Toast.makeText(this,
                try {
                    when (locale.country) {
                        "KR" -> { "Chat gpt 서버 수용량이 다찼거나 로그인이 필요합니다" }
                        "US" -> { "Chat gpt server full or login required" }
                        "JP" -> { "チャットgptサーバー容量が不足しているかログインが必要です" }
                        "DE" -> { "Chat-GPT-Server voll oder Anmeldung erforderlich"}
                        else -> { "Chat gpt server full or login required" }
                    }
                } catch (e: Exception) {
                    "Chat gpt server full or login required"
                }
                , Toast.LENGTH_SHORT).show()
        }

        //새로고침 버튼
        val btnRefresh = findViewById<ImageButton>(R.id.btn_refresh)
        btnRefresh.bringToFront()
        btnRefresh.setOnClickListener {
            webView.reload()
        }

        //카테고리 버튼 클릭시
        val categoriButton = findViewById<Button>(R.id.btn_categori)
//        categoriButton.isEnabled=false
//        Handler(Looper.getMainLooper()).postDelayed({ categoriButton.isEnabled=true },3000)

        categoriButton.setOnClickListener {
            //categori button 누를시 categori 액티비티 띄움
            try{ startActivity(Intent(this, CategoryActivity::class.java)) }
            catch (e:Exception) {
                Log.d("testddd", e.message.toString())
            }

        //count 3 이상일때 버튼 누를시 광고 띄움
            count++
            if (count % 5 == 0) {
                if (mInterstitialAd != null) {
                    mInterstitialAd?.show(this)
                } //광고를 보여줍니다.
                else {
                    Toast.makeText(applicationContext, "광고 로드 실패", Toast.LENGTH_SHORT)
                        .show()//광고를 불러오지 못했을때
                }
            }

        }

//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
//                super.onPageStarted(view, url, favicon)
//            }
//
//            override fun shouldOverrideUrlLoading(
//                view: WebView,
//                request: WebResourceRequest
//            ): Boolean {
//                return super.shouldOverrideUrlLoading(view, request)
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//                super.onPageFinished(view, url)
//            }
//        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            finish()
        }
    }

}