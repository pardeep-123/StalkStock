package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.vender.Model.MessageList
import kotlinx.android.synthetic.main.chat_adapter.view.*

class ChatAdapter(var mContext:Context,var chatList: ArrayList<MessageList>, var senderid:String) : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    lateinit var context: Context
    var click = 0
    var isOpenDot = false
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chat_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (senderid == chatList[position].senderId.toString())
        {
            holder.itemView.clOther.visibility = View.GONE
            holder.itemView.clMe.visibility = View.VISIBLE

            holder.itemView.tvMessageMe.text = chatList[position].message
            Glide.with(mContext).load(chatList[position].sender.image).into(holder.itemView.civme)
            holder.itemView.tvTimeMe.text =AppUtils.convertTimeStampToDateTime(chatList[position].created)
        }
        else {
            try {
                holder.itemView.clMe.visibility = View.GONE
                holder.itemView.clOther.visibility = View.VISIBLE
                Glide.with(mContext).load(chatList[position].sender.image)
                    .into(holder.itemView.civOther)

                holder.itemView.tvMessage.text = chatList[position].message
                holder.itemView.tvTime.text =
                    AppUtils.convertTimeStampToDateTime(chatList[position].created)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }


    }


}