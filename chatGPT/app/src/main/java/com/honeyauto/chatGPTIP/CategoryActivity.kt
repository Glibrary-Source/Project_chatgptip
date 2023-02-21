package com.honeyauto.chatGPTIP

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.honeyauto.chatGPTIP.databinding.ActivityCategoryBinding


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

        //언어선택 프래그먼트로 이동
        binding.btnLanguage.setOnClickListener {
            navController.navigate(R.id.action_global_selectLanguage)
        }


    }

}