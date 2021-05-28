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
import com.stalkstock.vender.Model.ModelVendorProductList.Body.Product
import com.stalkstock.vender.ui.ProductDetail
import kotlinx.android.synthetic.main.list_home_two.view.*
import kotlin.collections.ArrayList

class TestAdapter(var context: Context, var currentModel: ArrayList<Product>) :
    RecyclerView.Adapter<TestAdapter.RecyclerViewHolder>() {
    var inflater: LayoutInflater
    var rl_list: ImageView? = null

    inner class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.list_home_two, parent, false)
        val viewHolder: RecyclerViewHolder = RecyclerViewHolder(v)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val product = currentModel[position]
        holder.itemView.homeitemimage.loadImage(product.productImage[0].image)
        holder.itemView.itemname.setText(product.name)
        holder.itemView.kg.setText(product.productMeasurement.name)
        holder.itemView.editname.setText(product.productCategory.name)
        holder.itemView.lb.setText(product.mrp+"/"+product.productMeasurement.name)
        holder.itemView.edititem.setOnClickListener {
            val intent = Intent(
                context,
                ProductDetail::class.java
            )
            intent.putExtra("product_id",product.id.toString())

            context.startActivity(intent)
        }

        if (product.availability==1)
        {
            holder.itemView.notavble.visibility = View.GONE
            holder.itemView.avble.visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.notavble.visibility = View.VISIBLE
            holder.itemView.avble.visibility = View.GONE
        }
    }
    override fun getItemCount(): Int {
        return currentModel.size
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}