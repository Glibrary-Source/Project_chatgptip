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
import com.honeyauto.chatGPTIP.SelectLanguageDirections

@SuppressLint("NotifyDataSetChanged")
class SelectLanguageAdapter(languagedata: List<String>, buttontext: List<String>) : RecyclerView.Adapter<SelectLanguageAdapter.MyViewHolder>() {

    private val languageData = languagedata
    private val buttonText = buttontext

    init {
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonView: Button = itemView.findViewById(R.id.btn_itemlanguage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_selectlanguage, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.buttonView.text = buttonText[position]
        holder.buttonView.setOnClickListener {
            val action = SelectLanguageDirections.actionSelectLanguageToWordCategoryDialog(
                languageData[position]
            )
            holder.itemView.findNavController().navigate(action)
            MyGlobals.instance?.checkLanguage = languageData[position]
        }
    }

    override fun getItemCount(): Int {
        return languageData.size
    }
}