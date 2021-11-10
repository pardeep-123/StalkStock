package com.stalkstock.vender.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.Chat
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.Model.MessageList
import com.stalkstock.vender.Model.VendorBiddingListResponse
import com.stalkstock.vender.ui.BidDetail
import kotlinx.android.synthetic.main.bidproductlist.view.*
import kotlinx.android.synthetic.main.messagelist.view.*
import kotlinx.android.synthetic.main.row_cart.view.*

class MessageAdapter(
    var mContext: Context,
    var arrayList: ArrayList<MessageList>
) :
    RecyclerView.Adapter<MessageAdapter.RecyclerViewHolder>() {

    var mUtil=Util()
    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.messagelist, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        Glide.with(mContext).load(arrayList[position].sender.image).into(holder.itemView.iv_userimage)
        holder.itemView.tv_personName.text= arrayList[position].sender.firstName + " "+arrayList[position].sender.lastName
        holder.itemView.tv_messageTemplate.text= arrayList[position].lastMessage.message
        holder.itemView.tv_timeOFMessage.text= mUtil.toDate(arrayList[position].created.toString(),"hh:mm")
        holder.itemView.setOnClickListener {

            val intent = Intent(mContext, Chat::class.java)
            intent.putExtra("id",arrayList[position].id.toString())
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
