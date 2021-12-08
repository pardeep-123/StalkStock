package com.stalkstock.consumer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.activities.ProductDetailsActivity
import com.stalkstock.consumer.model.ModelProductVendorList.Body.ProductSeller
import com.stalkstock.utils.loadImage
import kotlinx.android.synthetic.main.row_product.view.*
import java.util.*

class ProductsAdapter(
    var context: ProductActivity,
    var currentModel: ArrayList<ProductSeller>,
    var currentDelivery_type: String?
) : RecyclerView.Adapter<ProductsAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_product, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.starCount.text = currentModel[position].name
        holder.itemView.txtLocation.text = currentModel[position].productVendor.ShopAddress
        holder.itemView.txtVendor.text = currentModel[position].productVendor.shopName
        holder.itemView.price.text = currentModel[position].mrp+"/"+if(currentModel[position].measurement==null) "" else currentModel[position].measurement.name
        holder.itemView.star.rating = currentModel[position].ratingCount.toFloat()

        if(currentModel[position].availability==0){
            holder.itemView.rlMain.setBackgroundColor(context.resources.getColor(R.color.light_grey))
            holder.itemView.tvOutOfStock.visibility=View.VISIBLE

        }else{
            holder.itemView.tvOutOfStock.visibility=View.GONE
            holder.itemView.rlMain.setBackgroundColor(context.resources.getColor(R.color.white))
            holder.itemView.setOnClickListener {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("product_id",currentModel[position].id.toString())
                intent.putExtra("deliveryType",currentDelivery_type)
                context.startActivity(intent)
            }
        }

        if(currentModel[position].productImage.isNotEmpty()) holder.itemView.img.loadImage(currentModel[position].productImage[0].image)


    }

    override fun getItemCount(): Int {
        return currentModel.size
    }

}