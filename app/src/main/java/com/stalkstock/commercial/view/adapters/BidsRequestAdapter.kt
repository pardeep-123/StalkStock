package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.RequestDetail
import com.stalkstock.commercial.view.model.BidingDetailResponse
import de.hdodenhof.circleimageview.CircleImageView

class BidsRequestAdapter : RecyclerView.Adapter<BidsRequestAdapter.MyViewHolder>() {

    var listBids= ArrayList<BidingDetailResponse.VendorBidingRequest>()
    lateinit var context: Context
    var click = 0
    var isOpenDot = false


    lateinit var clickItem: ClickItem

    interface ClickItem {

        fun clicked(position: Int,items: BidingDetailResponse.VendorBidingRequest)
    }

    fun onPerformClick(clickItem: ClickItem)
    {this.clickItem = clickItem}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.request_bids_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listBids.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.tvPrice.setText("$"+ " "+listBids[position].amount)
        holder.tvDetail.setText(listBids[position].vendorDetail.shopName)
        holder.tvName.setText(listBids[position].vendorDetail.firstName+" "+listBids[position].vendorDetail.lastName)
        Glide.with(context).load(listBids[position].vendorDetail.image).into(holder.civImage)

         holder.itemView.setOnClickListener {
            val item = listBids[position]
             clickItem.clicked(position,item) }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val tvPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvDetail: TextView = itemView.findViewById(R.id.tvDetail)
        val civImage: CircleImageView = itemView.findViewById(R.id.civImage)

    }

}