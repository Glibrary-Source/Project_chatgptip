package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.honeyauto.chatGPTIP.model.DetailWordModel
import com.honeyauto.chatGPTIP.model.LanguageWordModel
import java.util.*


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this@SplashActivity.finish()

        }, 2000)

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
                if (it.isSuccessful) {
                    MyGlobals.instance!!.detailModel =
                        it.result.toObject(DetailWordModel::class.java)
                }
            }

        MyGlobals.instance?.localCountry = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            applicationContext.resources.configuration.locales[0]
        } else {
            applicationContext.resources.configuration.locale
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

    }

}

