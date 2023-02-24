package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.honeyauto.chatGPTIP.adapter.WordListAdapter
import com.honeyauto.chatGPTIP.databinding.FragmentCategoryWordListBinding


class CategoryWordList : Fragment() {

    private lateinit var overViewModel: OverViewModel
    private lateinit var binding: FragmentCategoryWordListBinding
    private val keyWord by navArgs<CategoryWordListArgs>()

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is CategoryActivity) {
            mContext = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overViewModel = ViewModelProvider(requireActivity()).get(OverViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCategoryWordListBinding.inflate(inflater)

        val vWordRecycler = binding.rcWordlist
        val adapter = WordListAdapter(keyWord.sentencekeyword, MyGlobals.instance?.detailModel!!, mContext)
        vWordRecycler.layoutManager = LinearLayoutManager(requireContext())
        vWordRecycler.adapter = adapter

        return binding.root
    }

}