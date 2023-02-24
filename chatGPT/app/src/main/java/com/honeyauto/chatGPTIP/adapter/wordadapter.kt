package com.honeyauto.chatGPTIP.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.honeyauto.chatGPTIP.*
import com.honeyauto.chatGPTIP.model.DetailWordModel

@SuppressLint("NotifyDataSetChanged")
class WordListAdapter(keyword: String, detailModel: DetailWordModel) : RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    var sentence : List<String>

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

            Toast.makeText(holder.itemView.context,
                if(MyGlobals.instance!!.checkLanguage == "kr"){"복사되었습니다"}else{"Copy"}
                , Toast.LENGTH_SHORT).show()

            it.context.startActivity(
                Intent(it.context, MainActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            )
        }
    }

    override fun getItemCount(): Int {
        return sentence.size
    }

}