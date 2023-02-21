package com.honeyauto.chatGPTIP

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.honeyauto.chatGPTIP.databinding.ActivityCategoryBinding


class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        try{
            if (MyGlobals.instance?.checkLanguage == null) {
                navController.navigate(R.id.selectLanguage, null)
            } else {
                val action = SelectLanguageDirections.actionSelectLanguageToWordCategoryDialog(
                    MyGlobals.instance?.checkLanguage!!
                )
                navController.navigate(action)
            }
        } catch (e:Exception) {
            Log.d("testTag", e.toString())
        }

        binding.btnLanguage.setOnClickListener {
            when(MyGlobals.instance?.currentFragment) {
                "SelectLanguage" -> {
                    val action = SelectLanguageDirections.actionSelectLanguageSelf()
                    navController.navigate(action)
                }
                "CategoryWordList" -> {
                    val action = CategoryWordListDirections.actionCategoryWordListToSelectLanguage()
                    navController.navigate(action)
                }
                "WordCategoryDialog" -> {
                    val action = WordCategoryDialogDirections.actionWordCategoryDialogToSelectLanguage()
                    navController.navigate(action)
                }
                else -> {
                    val action = SelectLanguageDirections.actionSelectLanguageSelf()
                    navController.navigate(action)
                }
            }
        }
    }

}