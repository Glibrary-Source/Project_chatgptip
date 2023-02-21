package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.honeyauto.chatGPTIP.adapter.WordListAdapter
import com.honeyauto.chatGPTIP.databinding.FragmentCategoryWordListBinding


class CategoryWordList : Fragment() {

    private lateinit var overViewModel: OverViewModel
    private lateinit var binding: FragmentCategoryWordListBinding
    private val keyWord by navArgs<CategoryWordListArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overViewModel = ViewModelProvider(requireActivity()).get(OverViewModel::class.java)
        MyGlobals.instance?.currentFragment = "CategoryWordList"
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCategoryWordListBinding.inflate(inflater)

        val vWordRecycler = binding.rcWordlist
        val adapter = WordListAdapter(keyWord.categoriword)
        vWordRecycler.adapter = adapter



        return binding.root
    }

}