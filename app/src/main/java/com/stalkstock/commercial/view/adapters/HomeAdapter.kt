package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.commercial.view.activities.RequestDetail
import com.stalkstock.R
import com.stalkstock.commercial.view.model.BidingListResponse

import com.stalkstock.utils.custom.TitiliumBoldTextView
import com.stalkstock.utils.custom.TitiliumRegularTextView
import java.text.SimpleDateFormat

class HomeAdapter(var list: ArrayList<BidingListResponse.BodyX>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    lateinit var context: Context
    var click = 0
    var isOpenDot = false


    lateinit var clickDoctor: ClickDoctor

    interface ClickDoctor {

        fun clicked(position: Int)
    }

    fun onPerformClick(clickDoctor: ClickDoctor)
    {this.clickDoctor = clickDoctor}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.home_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val formatter = SimpleDateFormat("MMM dd, yyyy '|' HH:mm")
        val output: String = formatter.format(parser.parse(list[position].createdAt))

        holder.tvRequest.text = "Request ID:"+" "+list[position].requestNo
        holder.tvBid.text = "BID:"+" "+list[position].bidCount
        holder.tvCreatedDate.text = output
        /*holder.tvBidStatus.text = list[position].*/

       // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }


        holder.itemView.setOnClickListener {
            val intent = Intent(context, RequestDetail::class.java)
            intent.putExtra("bidId",list[position].id)
            context.startActivity(intent)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvRequest: TitiliumBoldTextView = itemView.findViewById(R.id.tvRequest)
        var tvBid: TitiliumBoldTextView = itemView.findViewById(R.id.tvBid)
        var tvCreatedDate: TitiliumRegularTextView = itemView.findViewById(R.id.tvCreatedDate)
       /* var tvBidStatus: TitiliumRegularTextView = itemView.findViewById(R.id.tvBidStatus)*/

    }

}
