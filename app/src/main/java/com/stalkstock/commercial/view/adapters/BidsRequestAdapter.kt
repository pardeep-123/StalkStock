package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.RequestDetail

class BidsRequestAdapter(var listBids: ArrayList<RequestDetail.BidsData>) : RecyclerView.Adapter<BidsRequestAdapter.MyViewHolder>() {

    lateinit var context: Context
    var click = 0
    var isOpenDot = false


    lateinit var clickItem: ClickItem

    interface ClickItem {

        fun clicked(position: Int)
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


         holder.itemView.setOnClickListener { clickItem.clicked(position) }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}