package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.vender.Model.MessageList
import kotlinx.android.synthetic.main.chat_adapter.view.*

class ChatAdapter(var mContext:Context,var chatList: ArrayList<MessageList>) : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.chat_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        /*if(chatList[position].type=="other")
        {
            holder.itemView.clOther.visibility = View.VISIBLE
            holder.itemView.clMe.visibility = View.GONE

            holder.itemView.tvMessage.text = chatList[position].message
            holder.itemView.tvTime.text = chatList[position].time
        }
        else
        {
            holder.itemView.clMe.visibility = View.VISIBLE
            holder.itemView.clOther.visibility = View.GONE

            holder.itemView.tvMessageMe.text = chatList[position].message
            holder.itemView.tvTimeMe.text = chatList[position].time
        }
*/



        // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }



    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}