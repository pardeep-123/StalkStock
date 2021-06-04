package com.stalkstock.consumer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.consumer.fragment.HomeCounsumerFragment
import com.stalkstock.utils.loadImage
import kotlin.collections.ArrayList

class CategoryAdapter(
    var context: HomeCounsumerFragment,
    var mContext: Context,
    var arrayList: ArrayList<ModelCategoryList.Body>
) :
    RecyclerView.Adapter<CategoryAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView
        var name: TextView

        init {
            img = view.findViewById(R.id.img)
            name = view.findViewById(R.id.starCount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_categrey, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.img.loadImage(arrayList[position].image)
        holder.name.text = arrayList[position].name
        holder.itemView.setOnClickListener {
            context.startSubCat(arrayList[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}