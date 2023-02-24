package com.honeyauto.chatGPTIP.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.honeyauto.chatGPTIP.MyGlobals
import com.honeyauto.chatGPTIP.R
import com.honeyauto.chatGPTIP.WordCategoryDetailDirections
import com.honeyauto.chatGPTIP.model.DetailWordModel


@SuppressLint("NotifyDataSetChanged")
class CategoryDetailAdapter(keyword: String, detailModel: DetailWordModel) :
    RecyclerView.Adapter<CategoryDetailAdapter.MyViewHolder>() {

    private var categorylist: List<String>
    private val middleKeyword = keyword

    init {
        categorylist =
            try { detailModel.categorylist!!["detail${MyGlobals.instance!!.checkLanguage}"]!![keyword]!!.keys.toList() }
            catch (e: Exception) { listOf() }
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonView: Button = itemView.findViewById(R.id.btn_detailword)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detailword, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.buttonView.text = categorylist[position]
        holder.buttonView.setOnClickListener {
            val action = WordCategoryDetailDirections.actionWordCategoryDetailToCategoryWordList(
                sentencekeyword = categorylist[position]
            )
            holder.itemView.findNavController().navigate(action)
            MyGlobals.instance!!.middleKeyword = middleKeyword
        }
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }
}