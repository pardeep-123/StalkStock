package com.stalkstock.consumer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.activities.ProductdetailsActivity
import com.stalkstock.consumer.model.ModelProductVendorList.Body.ProductSeller
import com.stalkstock.utils.loadImage
import kotlinx.android.synthetic.main.row_product.view.*
import java.util.*

class ProductsAdapter(
    var context: ProductActivity,
    var currentModel: ArrayList<ProductSeller>,
    var currentDelivery_type: String?
) : RecyclerView.Adapter<ProductsAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_product, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.starCount.setText(currentModel[position].name)
        holder.itemView.txtLocation.setText(currentModel[position].productVendor.geoLocation)
        holder.itemView.txtVendor.setText(currentModel[position].productVendor.shopName)
        holder.itemView.price.setText(currentModel[position].mrp+"/"+currentModel[position].measurement.name)
        holder.itemView.star.rating = currentModel[position].ratingCount.toFloat()
        holder.itemView.img.loadImage(currentModel[position].productImage[0].image.toString())

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductdetailsActivity::class.java)
            intent.putExtra("product_id",currentModel[position].id.toString())
            intent.putExtra("deliveryType",currentDelivery_type)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return currentModel.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}