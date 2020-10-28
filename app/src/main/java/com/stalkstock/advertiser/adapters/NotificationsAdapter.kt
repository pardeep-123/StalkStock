package com.stalkstock.advertiser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R

class NotificationsAdapter(
    val context: Context?,
    ) : RecyclerView.Adapter<NotificationsAdapter.AdsHolder>() {
    override fun onBindViewHolder(holder: AdsHolder, position: Int) {
            holder.bindItems(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(LayoutInflater.from(context).inflate(R.layout.notification_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to


        fun bindItems(position: Int) {

        }

    }
}

