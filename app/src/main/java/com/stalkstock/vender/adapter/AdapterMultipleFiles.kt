package com.stalkstock.vender.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.loadImage

import kotlinx.android.synthetic.main.item_sub_images.view.*

class AdapterMultipleFiles(
    var context: Context,
    var currentModel: ArrayList<String>
) :
    RecyclerView.Adapter<AdapterMultipleFiles.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var rl_list: ImageView? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.item_sub_images, parent, false)
        val viewHolder: RecyclerViewHolder = RecyclerViewHolder(v)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.adduploadimagesone.loadImage(currentModel[position])


    }

    override fun getItemCount(): Int {
        return currentModel.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}