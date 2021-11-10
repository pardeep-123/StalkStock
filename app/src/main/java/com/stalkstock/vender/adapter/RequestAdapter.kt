package com.stalkstock.vender.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.Model.VendorBiddingListResponse
import com.stalkstock.vender.ui.BidDetail
import kotlinx.android.synthetic.main.bidproductlist.view.*
import kotlinx.android.synthetic.main.row_cart.view.*

class RequestAdapter(
    var mContext: Context,
    var arrayList: ArrayList<BidData>
) :
    RecyclerView.Adapter<RequestAdapter.RecyclerViewHolder>() {

    var mUtil=Util()
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.bidproductlist, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.bidid.text = "BID:"+arrayList[position].bidCount
        holder.itemView.requestid.text = "Request Id: "+arrayList[position].requestNo.toString()
        holder.itemView.biddate.text= AppUtils.changeDateFormat(
            arrayList[position].createdAt.toString(),
            GlobalVariables.DATEFORMAT.DateTimeFormat1,
            GlobalVariables.DATEFORMAT.DateTimeFormat2
        )
//            mUtil.toDate(arrayList[position].createdAt.toString(),"MMM,dd,yyyy")
//        holder.itemView.bidtime.text=mUtil.toDate(arrayList[position].createdAt.toString(),"hh:mm")
//         holder.itemView.biddate.bidtime=


        holder.itemView.setOnClickListener {

            val intent = Intent(mContext, BidDetail::class.java)
            intent.putExtra("bidId",arrayList[position].id.toString())
            mContext.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
