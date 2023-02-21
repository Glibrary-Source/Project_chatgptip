package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("NotifyDataSetChanged")
class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var fireStore = FirebaseFirestore.getInstance()
    private val categoriList = ArrayList<WordModel>()
    init {
        fireStore.collection("catagori")
            .addSnapshotListener { querySnapshot, _ ->
                categoriList.clear()

                for (snapshot in querySnapshot!!.documents) {
                    val item = snapshot.toObject(WordModel::class.java)
                    categoriList.add(item!!)
                }
                notifyDataSetChanged()
            }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonView: Button = itemView.findViewById(R.id.buttonview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.catagori_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val item = telephoneBook[position]
        holder.buttonView.text = categoriList[position].keyword
        holder.buttonView.setOnClickListener {
            val action = WordCategoryDialogDirections.actionWordCategoryDialogToCategoryWordList2(
                categoriword = categoriList[position].keyword.toString()
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return categoriList.size
    }

}