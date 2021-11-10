package com.stalkstock.consumer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.OrderdeatilsActivity
import com.stalkstock.consumer.model.OrderDetailResponse
import kotlinx.android.synthetic.main.row_myorderdetailproduct.view.*

class MyorderProductAdapter(
    var context: Context,
    var mOrderArrayList: List<OrderDetailResponse.Body.OrderItem>
) : RecyclerView.Adapter<MyorderProductAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_myorderdetailproduct, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.name_text.text = mOrderArrayList[position].product.name
        holder.itemView.tvPrice.text = "$"+mOrderArrayList[position].product.mrp

    }

    override fun getItemCount(): Int {
        return mOrderArrayList.size
    }
    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

}