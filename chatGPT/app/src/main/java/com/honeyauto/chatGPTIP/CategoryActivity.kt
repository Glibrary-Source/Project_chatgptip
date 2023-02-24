package com.honeyauto.chatGPTIP

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.honeyauto.chatGPTIP.databinding.ActivityCategoryBinding
import java.util.*


class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //뷰반인딩
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //네이브 그래프 네비게이션 컨트롤러
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //언어를 선택하면 다시 언어선택 안뜨게
        if (MyGlobals.instance?.checkLanguage == null) {
            navController.navigate(R.id.selectLanguage, null)
        } else {
            val action = SelectLanguageDirections.actionSelectLanguageToWordCategoryDialog(
                MyGlobals.instance?.checkLanguage!!
            )
            navController.navigate(action)
        }

        //어떤 언어가 효과적인지 설명하는 텍스트뷰에 현제 나라위치 가져와서 번역해줌
        findViewById<TextView>(R.id.tv_example_effect).text =
            try {
                when (MyGlobals.instance?.localCountry?.country) {
                    "KR" -> { "영문 일때 가장 효과적입니다" }
                    "US" -> { "The best effect when used in English" }
                    "JP" -> { "えいご いちばんこ うかてき" }
                    "DE" -> { "Am effektivsten auf Englisch"}
                    null -> { "The best effect when used in English" }
                    else -> { "The best effect when used in English" }
                }
            } catch (e: Exception) {
                "The best effect when used in English"
            }

        //언어선택 프래그먼트로 이동
        binding.btnLanguage.setOnClickListener {
            navController.navigate(R.id.action_global_selectLanguage)
        }


    }

}