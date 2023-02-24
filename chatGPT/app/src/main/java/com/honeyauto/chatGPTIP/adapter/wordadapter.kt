package com.honeyauto.chatGPTIP.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.honeyauto.chatGPTIP.*
import com.honeyauto.chatGPTIP.model.DetailWordModel


@SuppressLint("NotifyDataSetChanged")
class WordListAdapter(keyword: String, detailModel: DetailWordModel, context: Context) : RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    var sentence : List<String>
    var mContext = context

    init {
        sentence = try{ detailModel.categorylist!!["detail${MyGlobals.instance!!.checkLanguage}"]!![MyGlobals.instance!!.middleKeyword]!![keyword]!!.toList() }
        catch (e:Exception) { listOf() }
        notifyDataSetChanged()
    }

    class WordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordButtonView: TextView = itemView.findViewById(R.id.btn_word)
        var clipboard = itemView.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wordlist, parent, false)
        return WordListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
        holder.wordButtonView.text = sentence[position]
        holder.wordButtonView.setOnClickListener {
            val clipboardManager = holder.clipboard
            val clipData = ClipData.newPlainText("copyText", holder.wordButtonView.text)
            clipboardManager.setPrimaryClip(clipData)
            (mContext as Activity).finish()
        }
    }

    override fun getItemCount(): Int {
        return sentence.size
    }

}