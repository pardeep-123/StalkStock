package com.stalkstock.consumer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.HomedetailsActivity
import com.stalkstock.consumer.activities.ProductActivity
import com.stalkstock.consumer.model.ModelProductListAsPerSubCat
import com.stalkstock.utils.loadImage
import kotlinx.android.synthetic.main.row_homedetails.view.*
import java.util.ArrayList

class HomedetailAdapter(
    var context: HomedetailsActivity,
    var currentModel: ArrayList<ModelProductListAsPerSubCat.Body>,
    var currentDeliveryType: String
) :
    RecyclerView.Adapter<HomedetailAdapter.RecyclerViewHolder>() {
    var inflater : LayoutInflater

    class RecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(
        view!!
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.row_homedetails, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val body = currentModel[position]
        holder.itemView.img.loadImage(body.productImage[0].image)
        holder.itemView.starCount.setText(body.name)
        holder.itemView.price.setText("$"+body.mrp+"/")
        holder.itemView.star.visibility = View.GONE

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductActivity::class.java)
            intent.putExtra("product_id",body.id.toString())
            intent.putExtra("title",body.name.toString())
            intent.putExtra("delivery_type",currentDeliveryType)
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