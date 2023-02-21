package com.honeyauto.chatGPTIP.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.honeyauto.chatGPTIP.*


@SuppressLint("NotifyDataSetChanged")
class WordListAdapter(private val keyWord: String) : RecyclerView.Adapter<WordListAdapter.WordListViewHolder>() {

    private var fireStore = FirebaseFirestore.getInstance()
    var wordList: ArrayList<WordModel> = arrayListOf()
    private var keyWordList = mutableListOf<String>()

    init {
        fireStore.collection("catagori")
            .addSnapshotListener { querySnapshot, _ ->
                // ArrayList 비워줌
                wordList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    val item = snapshot.toObject(WordModel::class.java)
                    if(item?.keyword == keyWord) {
                        when(MyGlobals.instance!!.checkLanguage) {
                            "kr" -> { keyWordList = item.krsentence as MutableList<String> }
                            "en" -> { keyWordList = item.ensentence as MutableList<String> }
                        }
                    }
                    Log.d("testDBF", keyWordList.toString())
                }
                notifyDataSetChanged()
            }
    }

    class WordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordButtonView: TextView = itemView.findViewById(R.id.btn_word)
        var clipboard = itemView.context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_item, parent, false)
        return WordListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordListViewHolder, position: Int) {
//        val item = data[position]
        holder.wordButtonView.text = keyWordList[position]
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
        return keyWordList.size
    }
}