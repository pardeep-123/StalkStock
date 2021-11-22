package com.stalkstock.vender.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.utils.loadImage

import kotlinx.android.synthetic.main.item_sub_images.view.*

class AdapterMultipleFiles(
    var context: Context,
    var currentModel: ArrayList<AddEditImageModel>
) :
    RecyclerView.Adapter<AdapterMultipleFiles.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var rl_list: ImageView? = null
    lateinit var multipleFileInterface:MultipleFilesInterface

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.item_sub_images, parent, false)
        val viewHolder: RecyclerViewHolder = RecyclerViewHolder(v)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        Glide.with(context).load(currentModel[position].name).into( holder.itemView.adduploadimagesone)

        holder.itemView.ivDeletePhoto.setOnClickListener {
            multipleFileInterface.onItemClick(position,currentModel[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return currentModel.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    interface MultipleFilesInterface{
        fun onItemClick(position: Int,data:AddEditImageModel)
    }
}