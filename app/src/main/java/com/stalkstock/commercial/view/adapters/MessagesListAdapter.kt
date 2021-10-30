package com.live.stalkstockcommercial.ui.view.adapters.messages

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.commercial.view.model.ModelPojo
import com.stalkstock.commercial.view.activities.Chat
import com.stalkstock.R
import kotlinx.android.synthetic.main.item_messages.view.*

class MessagesListAdapter(var context: Context, var list:ArrayList<ModelPojo.MessageListModel>, var listner:OnMessageRecyclerViewListAdapter):RecyclerView.Adapter<MessagesListAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_messages, parent, false))
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initalize(list, position)
        holder.itemView.setOnClickListener { context.startActivity(Intent(context, Chat::class.java)) }
    }
    override fun getItemCount(): Int {
        return list.size
    }
    inner  class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val image = itemView.iv_userimage
        val name = itemView.tv_personName
        val message = itemView.tv_messageTemplate
        val time = itemView.tv_timeOFMessage
        val numberOfMessages = itemView.tv_numberOfMessages
        fun initalize(list: ArrayList<ModelPojo.MessageListModel>, position: Int){
            image.setImageResource(list.get(position).image)
            name.text = list.get(position).name
            message.text = list.get(position).message
            time.text = list.get(position).time
            numberOfMessages.text = list.get(position).numberOFMessages.toString()
           //logic for showing  number of messages in red dot ..
            if(numberOfMessages.text.equals("0") or numberOfMessages.text.isNullOrEmpty()){
                numberOfMessages.visibility= View.GONE
                time.setTextColor(Color.parseColor("#000000"))
            }else{
                numberOfMessages.visibility= View.VISIBLE
/*
                time.setTextColor(Color.parseColor("#459972"))
*/
                time.setTextColor(Color.parseColor("#7DB733"))
            }
            //click for open chat activity...

        }
    }

    interface OnMessageRecyclerViewListAdapter{
        fun onMessgaeListItemClickListner(list: ArrayList<ModelPojo.MessageListModel>, position: Int)
    }
}