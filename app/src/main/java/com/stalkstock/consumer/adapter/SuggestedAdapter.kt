package com.stalkstock.consumer.adapter

import com.stalkstock.driver.models.SuggestedBody
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.stalkstock.R
import kotlinx.android.synthetic.main.row_suggestedforyou.view.*
import java.util.ArrayList

class SuggestedAdapter(var listSuggested: ArrayList<SuggestedBody>) :
    RecyclerView.Adapter<SuggestedAdapter.RecyclerViewHolder>() {

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.findViewById(R.id.ivImage)
        var name: TextView = view.findViewById(R.id.tvTitle)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_suggestedforyou, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.tvTitle.text  = listSuggested[position].name
        Glide.with(holder.itemView.context).load(listSuggested[position].productImage[0].image).into(holder.itemView.ivImage)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return listSuggested.size
    }

}