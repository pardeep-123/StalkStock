package com.stalkstock.consumer.adapter

import com.stalkstock.consumer.activities.ProductDetailsActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.TextView
import com.stalkstock.R
import android.view.ViewGroup
import android.view.View
import com.stalkstock.consumer.model.SellerProduct
import com.stalkstock.consumer.model.UserVendorsProductList
import com.stalkstock.utils.loadImage
import kotlinx.android.synthetic.main.row_productdetsils.view.*
import java.util.ArrayList

class ProductsdetailsAdapter(
    var context: ProductDetailsActivity,
    var currentModel: ArrayList<SellerProduct>
) : RecyclerView.Adapter<ProductsdetailsAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var add: TextView = view.findViewById(R.id.add)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_productdetsils, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.starCount.text = currentModel[position].title+" "+currentModel[position].name
       // holder.itemView.location_text.visibility = View.VISIBLE
        holder.itemView.description_textbox.text= currentModel[position].description
       // holder.itemView.location_text.text= currentModel[position]
        holder.itemView.price.text = "$"+currentModel[position].mrp+"/"+if(currentModel[position].measurement==null) "" else currentModel[position].measurement.name
        holder.itemView.star.rating = currentModel[position].ratingCount.toFloat()

        /*if(currentModel[position].productType==1){
            holder.itemView.imgVegNon.setImageResource(R.drawable.red_dot)
        }else{
            holder.itemView.imgVegNon.setImageResource(R.drawable.green_dot)
        }*/

        if(currentModel[position].productImage.isNotEmpty()) holder.itemView.img.loadImage(currentModel[position].productImage[0].image)

        val cart = currentModel[position].cart
        var currentQty = 0
        if (cart != null && cart.quantity > 0) {
            holder.add.visibility = View.GONE
            currentQty = cart.quantity
            holder.itemView.ltPlusMinus.visibility = View.VISIBLE
            holder.itemView.count.text = cart.quantity.toString()
        }
        else {
            holder.add.visibility = View.VISIBLE
            holder.itemView.ltPlusMinus.visibility = View.GONE
        }

        holder.itemView.minus.setOnClickListener {
            val i = currentQty - 1
            context.addToCartAPI(currentModel[position].id.toString(), i.toString())
        }
        holder.itemView.plus.setOnClickListener {
            val i = currentQty + 1
            context.addToCartAPI(currentModel[position].id.toString(), i.toString())
        }
        holder.add.setOnClickListener {
            val i = currentQty + 1
            context.addToCartAPI(currentModel[position].id.toString(), i.toString())
        }
    }

    override fun getItemCount(): Int {
        return currentModel.size
    }

}