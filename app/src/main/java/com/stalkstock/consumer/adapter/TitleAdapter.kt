package com.stalkstock.consumer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.HomedetailsActivity
import com.stalkstock.utils.SliderItemTitleModel
import java.util.*

class TitleAdapter(var context: HomedetailsActivity, var arrayList: ArrayList<SliderItemTitleModel>) :
    RecyclerView.Adapter<TitleAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_item_title: TextView = view.findViewById(R.id.tv_item_title)
        var tv_item_title_bold: TextView = view.findViewById(R.id.tv_item_title_bold)
        var view_simple: View = view.findViewById(R.id.view_simple)
        var view_bold: View = view.findViewById(R.id.view_bold)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_title, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.tv_item_title.text = arrayList[position].title
        holder.tv_item_title_bold.text = arrayList[position].title
        if (arrayList[position].isSelected == "false") {
            holder.tv_item_title_bold.visibility = View.GONE
            holder.view_bold.visibility = View.GONE
            holder.tv_item_title.visibility = View.VISIBLE
            holder.view_simple.visibility = View.VISIBLE
        } else if (arrayList[position].isSelected == "true") {
            holder.tv_item_title_bold.visibility = View.VISIBLE
            holder.view_bold.visibility = View.VISIBLE
            holder.tv_item_title.visibility = View.GONE
            holder.view_simple.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            if(arrayList[position].isSelected!="true") {
                for (i in arrayList.indices) {
                    arrayList[i].isSelected = "false"
                }
                arrayList[position].isSelected = "true"
                notifyDataSetChanged()
                context.setSelectedSubCategoryID(arrayList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}