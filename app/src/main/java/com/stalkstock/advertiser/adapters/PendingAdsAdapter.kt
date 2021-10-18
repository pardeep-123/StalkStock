package com.stalkstock.advertiser.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.activities.AdDetailActivity
import com.stalkstock.advertiser.model.BusinessAdsList
import java.text.SimpleDateFormat

class PendingAdsAdapter(
    val context: Context,
    val adsList: List<BusinessAdsList.Body> = ArrayList()

    ) : RecyclerView.Adapter<PendingAdsAdapter.AdsHolder>() {
    override fun onBindViewHolder(holder: AdsHolder, position: Int) {
            holder.bindItems(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(LayoutInflater.from(context).inflate(R.layout.home_card, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return adsList.size
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val rel_approved = itemView.findViewById(R.id.rel_approved) as RelativeLayout
        val rel_expired = itemView.findViewById(R.id.rel_expired) as RelativeLayout
        val rel_pending = itemView.findViewById(R.id.rel_pending) as RelativeLayout
        val tvAdsTitle = itemView.findViewById<TextView>(R.id.tvAdsTitle)
        val tvAdsStartDate = itemView.findViewById<TextView>(R.id.tvAdsStartDate)
        val tvAdsDescription = itemView.findViewById<TextView>(R.id.tvAdsDescription)
        val ivAdsImages = itemView.findViewById<ImageView>(R.id.ivAdsImages)

        fun bindItems(position: Int) {
            rel_approved.visibility = View.GONE
            rel_expired.visibility = View.GONE
            rel_pending.visibility = View.VISIBLE

            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val formatter = SimpleDateFormat("MMM dd, yyyy 'AT' HH:mm aaa")
            val output: String = formatter.format(parser.parse(adsList[position].createdAt))

            tvAdsTitle.text = adsList[position].title
            tvAdsDescription.text = adsList[position].description
            tvAdsStartDate.text = output
            Glide.with(context).load(adsList[position].bannerImages[0].image).into(ivAdsImages)

            itemView.setOnClickListener{
                val intent = Intent(context, AdDetailActivity::class.java)
                intent.putExtra("model",adsList[position])
                intent.putExtra("intentfrom","pending")
                context.startActivity(intent)
            }
        }
    }
}

