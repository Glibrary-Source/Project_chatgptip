package com.honeyauto.chatGPTIP

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.honeyauto.chatGPTIP.databinding.FragmentSelectLanguageBinding

class SelectLanguage : Fragment() {

    lateinit var binding: FragmentSelectLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyGlobals.instance?.currentFragment = "SelectLanguage"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectLanguageBinding.inflate(inflater)

        binding.btnKr.setOnClickListener {
            val action = SelectLanguageDirections.actionSelectLanguageToWordCategoryDialog("kr")
            MyGlobals.instance?.checkLanguage = "kr"
            findNavController().navigate(action)
        }

        binding.btnEn.setOnClickListener {
            val action = SelectLanguageDirections.actionSelectLanguageToWordCategoryDialog("en")
            MyGlobals.instance?.checkLanguage = "en"
            findNavController().navigate(action)
        }

        return binding.root
    }

}