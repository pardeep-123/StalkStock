package com.stalkstock.rating

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.model.OrderDetailResponse
import kotlinx.android.synthetic.main.item_view_product_rating.view.*

class RatingProductAdapter(
    var mContext: Context,
    var arrayList: ArrayList<OrderDetailResponse.Body.OrderItem>
) :
    RecyclerView.Adapter<RatingProductAdapter.RecyclerViewHolder>() {

    lateinit var ratingProductInterface: RatingProductInterface
    var inflater: LayoutInflater = LayoutInflater.from(mContext)

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.item_view_product_rating, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.tvProduct.text= "Give rating to "+arrayList[position].product.name
        var rating= holder.itemView.ratingProduct.rating.toString()

        holder.itemView.btnSubmit.setOnClickListener {
            ratingProductInterface.onSubmitRating(position,rating,arrayList[position].id.toString(),arrayList[position].productId.toString())
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    interface RatingProductInterface{
        fun onSubmitRating(position: Int,rating:String,orderItemId:String,productId:String)
    }
}