package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.bidprdouctaccpetlist.view.*

class AccpetAdapter(
    var mContext: Context,
    var arrayList: ArrayList<BidData>
) :
    RecyclerView.Adapter<AccpetAdapter.RecyclerViewHolder>() {

    var mUtil= Util()
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.bidprdouctaccpetlist, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.requestid.text = "Request Id: "+arrayList[position].requestNo
        holder.itemView.bidPrice.text = arrayList[position].vendorBidingRequest.amount
        holder.itemView.bidCount.text = "Bid: "+arrayList[position].bidCount.toString()
        Glide.with(mContext).load(arrayList[position].commercialDetail.image).into(holder.itemView.bidimguser as CircleImageView)
        holder.itemView.bidusername.text =arrayList[position].commercialDetail.firstName +" "+arrayList[position].commercialDetail.lastName
        holder.itemView.biddate.text= AppUtils.changeDateFormat(
            arrayList[position].createdAt.toString(),
            GlobalVariables.DATEFORMAT.DateTimeFormat1,
            GlobalVariables.DATEFORMAT.DateTimeFormat2
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
