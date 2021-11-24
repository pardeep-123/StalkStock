package com.stalkstock.rating

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.common.model.ModelCategoryList
import com.stalkstock.consumer.adapter.CategoryAdapter
import com.stalkstock.consumer.fragment.HomeCounsumerFragment
import com.stalkstock.utils.loadImage
import kotlin.collections.ArrayList

class RatingProductAdapter(
    var context: RatingActivity,
    var mContext: Context,
    var arrayList: ArrayList<ModelCategoryList.Body>
) :
    RecyclerView.Adapter<RatingProductAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(mContext)

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var img: ImageView = view.findViewById(R.id.img)
        var name: TextView = view.findViewById(R.id.starCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_categrey, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.img.loadImage(arrayList[position].image)
        holder.name.text = arrayList[position].name
        holder.itemView.setOnClickListener {
            //context.startSubCat(arrayList[position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}