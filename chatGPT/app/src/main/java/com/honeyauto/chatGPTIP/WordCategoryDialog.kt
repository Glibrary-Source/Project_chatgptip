package com.honeyauto.chatGPTIP

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.honeyauto.chatGPTIP.databinding.FragmentWordCategoryDialogBinding

class WordCategoryDialog : Fragment() {

    private lateinit var binding: FragmentWordCategoryDialogBinding
    private lateinit var overViewModel: OverViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overViewModel = ViewModelProvider(requireActivity()).get(OverViewModel::class.java)
        MyGlobals.instance?.currentFragment = "WordCategoryDialog"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentWordCategoryDialogBinding.inflate(inflater)

        val rcView = binding.rcCategorylist
        val adapter = MyAdapter()
        rcView.adapter = adapter
        rcView.layoutManager = GridLayoutManager(requireContext(),2)

        Log.d("testLog", "Asdascazcxzc")

        return binding.root
    }
}