package com.honeyauto.chatGPTIP

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.honeyauto.chatGPTIP.adapter.CategoryDetailAdapter
import com.honeyauto.chatGPTIP.databinding.FragmentWordCategoryDetailBinding

class WordCategoryDetail : Fragment() {

    lateinit var binding: FragmentWordCategoryDetailBinding
    private val categoryKey by navArgs<WordCategoryDetailArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentWordCategoryDetailBinding.inflate(inflater)

        val rcView = binding.rcCategoryDetail
        val adapter = CategoryDetailAdapter(categoryKey.keyword, MyGlobals.instance?.detailModel!!)
        rcView.adapter = adapter
        rcView.layoutManager = GridLayoutManager(requireContext(), 2)



        return binding.root
    }
}