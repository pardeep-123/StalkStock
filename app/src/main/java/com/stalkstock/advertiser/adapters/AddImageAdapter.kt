package com.stalkstock.advertiser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.interfaces.OnClick
import com.stalkstock.R

class AddImageAdapter(val context: Context,
                      private val adsList: ArrayList<String>,
                      var onclick: OnClick

                      ): RecyclerView.Adapter<AddImageAdapter.AddHolder>() {
    class AddHolder(itemViw: View): RecyclerView.ViewHolder(itemViw) {
        var ivImageAds = itemViw.findViewById<ImageView>(R.id.ivImageAds)
        var relImagePicker = itemViw.findViewById<RelativeLayout>(R.id.relImagePicker)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddImageAdapter.AddHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.add_post_post,parent,false)
        return AddHolder(view)
    }

    override fun onBindViewHolder(holder: AddImageAdapter.AddHolder, position: Int) {
        if(adsList[position].trim().isNotEmpty()) {
            Glide.with(context).load(adsList[position]).centerCrop().into(holder.ivImageAds)
        }

            if(position==adsList.size-1)
            {
                holder.relImagePicker.visibility = VISIBLE
            }
            else{
                holder.relImagePicker.visibility = GONE
            }
            holder.relImagePicker.setOnClickListener {
                /*      if(adsList.size>=4)
                      {
                          Toast.makeText(context,"Max",Toast.LENGTH_SHORT).show()
                      }*/
                //   else{

                onclick.onclick(position)
                //    }
            }


    }

    override fun getItemCount(): Int {

     return adsList.size
    }
}