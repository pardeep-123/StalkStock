package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_add_product.view.*

import kotlinx.android.synthetic.main.item_sub_images.view.*

class AdapterMultipleFiles(
    var context: Context,

    var currentModel: ArrayList<AddEditImageModel>,var firstImage:String
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

    fun firstImageUpdate(image: String, arrStringMultipleImages: ArrayList<AddEditImageModel>){
        currentModel=arrStringMultipleImages
        firstImage=image
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if(position==0){
            holder.itemView.adduploadimagesone.setImageResource(R.drawable.camera_green)
        }else{
            holder.itemView.adduploadimagesone.setImageResource(R.drawable.camera_grey)
        }
        if(firstImage.isEmpty()){
            if(position==0){
                Glide.with(context).load(R.drawable.camera_green).into( holder.itemView.adduploadimagesone)
            }else{
                Glide.with(context).load(R.drawable.camera_grey).into( holder.itemView.adduploadimagesone)
            }

        }else{
            try {
                Glide.with(context).load(currentModel[position].name).placeholder(R.drawable.camera_green).into( holder.itemView.adduploadimagesone)
            } catch (e: Exception) {
                if(position==0){
                    Glide.with(context).load(R.drawable.place_holder).placeholder(R.drawable.camera_green).into(holder.itemView.adduploadimagesone)

                }else{
                    Glide.with(context).load(R.drawable.place_holder).placeholder(R.drawable.camera_grey).into(holder.itemView.adduploadimagesone)

                }
            }
        }



        holder.itemView.adduploadimagesone.setOnClickListener {
            multipleFileInterface.onImageClick(position)
        }

        holder.itemView.ivDeletePhoto.setOnClickListener {
            multipleFileInterface.onItemClick(position,currentModel[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return 9
    }

    init {
        inflater = LayoutInflater.from(context)
    }

    interface MultipleFilesInterface{
        fun onItemClick(position: Int,data:AddEditImageModel)
        fun onImageClick(position: Int)
    }
}