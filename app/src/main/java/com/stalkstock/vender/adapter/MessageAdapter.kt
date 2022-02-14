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
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.vender.Model.MessageList
import kotlinx.android.synthetic.main.messagelist.view.*

class MessageAdapter(
    var mContext: Context,
    var arrayList: ArrayList<MessageList>
) :
    RecyclerView.Adapter<MessageAdapter.RecyclerViewHolder>() {

    var receiverId=""

    var inflater: LayoutInflater

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val v = inflater.inflate(R.layout.messagelist, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        if(arrayList[position].senderId==getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)){
            holder.itemView.tv_personName.text= arrayList[position].receiver.firstName + " "+arrayList[position].receiver.lastName
            Glide.with(mContext).load(arrayList[position].receiver.image).into(holder.itemView.iv_userimage)

        }else{
            holder.itemView.tv_personName.text= arrayList[position].sender.firstName + " "+arrayList[position].sender.lastName
            Glide.with(mContext).load(arrayList[position].sender.image).into(holder.itemView.iv_userimage)

        }


        holder.itemView.tv_messageTemplate.text= arrayList[position].lastMessage.message
        holder.itemView.tv_timeOFMessage.text= AppUtils.convertTimeStampToDateTime(arrayList[position].created.toLong())

        holder.itemView.setOnClickListener {

            val intent = Intent(mContext, Chat::class.java)
            if (arrayList[position].receiverId == getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)){
                intent.putExtra("userId",arrayList[position].senderId)
                intent.putExtra("userName", arrayList[position].sender.firstName + " "+arrayList[position].sender.lastName)
                intent.putExtra("userImage",arrayList[position].sender.image.toString())

            }else{
                intent.putExtra("userImage",arrayList[position].receiver.image.toString())
                intent.putExtra("userName", arrayList[position].receiver.firstName + " "+arrayList[position].receiver.lastName)


                intent.putExtra("userId",arrayList[position].receiverId)
            }

            if(arrayList[position].bidId==0){
                intent.putExtra("id",arrayList[position].orderId.toString())
                intent.putExtra("param_name","orderId")
            }else{
                intent.putExtra("id",arrayList[position].bidId.toString())
                intent.putExtra("param_name","bidId")
            }

            intent.putExtra("chatId",arrayList[position].id.toString())
            if (arrayList[position].receiverId == getPrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, 0)){
                intent.putExtra("userId",arrayList[position].senderId)

            }else{
                intent.putExtra("userId",arrayList[position].receiverId)
            }
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
