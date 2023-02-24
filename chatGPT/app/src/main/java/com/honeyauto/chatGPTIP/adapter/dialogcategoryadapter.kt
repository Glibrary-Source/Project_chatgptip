package com.honeyauto.chatGPTIP

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.honeyauto.chatGPTIP.model.DetailWordModel

@SuppressLint("NotifyDataSetChanged")
class CategoryDialogAdapter(categoryDB: DetailWordModel) :
    RecyclerView.Adapter<CategoryDialogAdapter.MyViewHolder>() {

    private val categoryDb = categoryDB
    private var categorylist: List<String>

    init {
        categorylist =
            try{ categoryDb.categorylist!!["detail${MyGlobals.instance!!.checkLanguage}"]!!.keys.toList() }
            catch (e:Exception) { listOf() }
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonView: Button = itemView.findViewById(R.id.btn_dialog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dialogcategory, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.buttonView.text = categorylist[position]
        holder.buttonView.setOnClickListener {
            val action = WordCategoryDialogDirections.actionWordCategoryDialogToWordCategoryDetail(
                keyword = categorylist[position]
            )
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return categorylist.size
    }
}