package com.stalkstock.advertiser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.model.NotificationListBody

class NotificationsAdapter(
    val list: MutableList<NotificationListBody>
    ) : RecyclerView.Adapter<NotificationsAdapter.AdsHolder>() {

    lateinit var notificationClick: NotificationClick

    override fun onBindViewHolder(holder: AdsHolder, position: Int) {
        holder.tvText.text = list[position].message
        holder.itemView.setOnClickListener {
            notificationClick.OnItemClick(position,list[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(LayoutInflater.from(parent.context).inflate(R.layout.notification_card, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {
       var tvText = view.findViewById<TextView>(R.id.tvText)

    }

    interface NotificationClick{
        fun OnItemClick(position: Int,data:NotificationListBody)
    }

}

