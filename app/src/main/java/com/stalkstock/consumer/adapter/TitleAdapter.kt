package com.stalkstock.consumer.adapter

import android.content.Context
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
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_item_title: TextView
        var tv_item_title_bold: TextView
        var view_simple: View
        var view_bold: View

        init {
            tv_item_title = view.findViewById(R.id.tv_item_title)
            tv_item_title_bold = view.findViewById(R.id.tv_item_title_bold)
            view_simple = view.findViewById(R.id.view_simple)
            view_bold = view.findViewById(R.id.view_bold)
        }
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
        } else {
        }
        holder.itemView.setOnClickListener {
            for (i in arrayList.indices) {
                arrayList[i].isSelected = "false"
            }
            arrayList[position].isSelected = "true"
            notifyDataSetChanged()

            context.setSelectedSubCategoryID(arrayList[position])
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}