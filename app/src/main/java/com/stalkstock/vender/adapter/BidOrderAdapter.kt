package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.OrderItem
import kotlinx.android.synthetic.main.item_view_bid_order_adapter.view.*

class BidOrderAdapter(
    var mContext: Context,
    var arrayList: ArrayList<OrderItem>
) :
    RecyclerView.Adapter<BidOrderAdapter.RecyclerViewHolder>() {

    var mUtil= Util()
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.item_view_bid_order_adapter, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.tvSNo.text= (position+1).toString()+"."
        holder.itemView.tvCategory.text= arrayList[position].product.categoryName
        holder.itemView.tvProduct.text= arrayList[position].product.name
        holder.itemView.tvQuantity.text= arrayList[position].qty.toString()
        holder.itemView.tvQuantityType.text= arrayList[position].product.measurementName
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
