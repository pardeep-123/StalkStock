package com.stalkstock.vender.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import kotlinx.android.synthetic.main.bidproductlist.view.*

class RequestAdapter(
    var mContext: Context,
    var arrayList: ArrayList<BidData>,var requestInterface:RequestInterface
) :
    RecyclerView.Adapter<RequestAdapter.RecyclerViewHolder>() {
  //  lateinit var requestInterface:RequestInterface

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
        holder.itemView.setOnClickListener {

            requestInterface.OnItemClick(position,arrayList[position].id.toString())


        }

    }


    interface RequestInterface{
        fun OnItemClick(position: Int,id:String)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    init {
        inflater = LayoutInflater.from(mContext)
    }
}
