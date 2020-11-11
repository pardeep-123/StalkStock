package com.stalkstock.advertiser.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.AdDetailActivity


class PendingAdsAdapter(
    val context: Context?
    ) : RecyclerView.Adapter<PendingAdsAdapter.AdsHolder>() {
    override fun onBindViewHolder(holder: AdsHolder, position: Int) {
            holder.bindItems(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(LayoutInflater.from(context).inflate(R.layout.home_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val rel_approved = itemView.findViewById(R.id.rel_approved) as RelativeLayout
        val rel_expired = itemView.findViewById(R.id.rel_expired) as RelativeLayout
        val rel_pending = itemView.findViewById(R.id.rel_pending) as RelativeLayout

        fun bindItems(position: Int) {
            rel_approved.visibility = View.GONE
            rel_expired.visibility = View.GONE
            rel_pending.visibility = View.VISIBLE
            itemView.setOnClickListener{
                context?.startActivity(Intent(context, AdDetailActivity::class.java))
            }
        }

    }
}

