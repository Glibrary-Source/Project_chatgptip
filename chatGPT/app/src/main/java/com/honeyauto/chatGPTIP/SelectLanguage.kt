package com.honeyauto.chatGPTIP

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.honeyauto.chatGPTIP.adapter.SelectLanguageAdapter
import com.honeyauto.chatGPTIP.databinding.FragmentSelectLanguageBinding

class SelectLanguage : Fragment() {

    lateinit var binding: FragmentSelectLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectLanguageBinding.inflate(inflater)

        val vLanguageRecycler = binding.rcLanguageList
        val selectAdapter = SelectLanguageAdapter(
            MyGlobals.instance?.languageList!!,
            MyGlobals.instance?.buttontext!!
        )
        vLanguageRecycler.adapter = selectAdapter

        return binding.root
    }

}