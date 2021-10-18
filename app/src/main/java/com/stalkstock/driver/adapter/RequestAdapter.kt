package com.stalkstock.driver.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.driver.models.HistoryDataBody
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables

class RequestAdapter(var listRequest: MutableList<HistoryDataBody>) : RecyclerView.Adapter<RequestAdapter.AdsHolder>() {


    lateinit var clickInterFace:OnClick

    override fun onBindViewHolder(holder: AdsHolder, position: Int) {

        holder.tvOrder.text ="Order ID : ${listRequest[position].orderNo}"
        holder.tvAddress.text =listRequest[position].vendorDetail.shopAddress

        Log.e("onBindViewHolder","====${listRequest[position].orderStatus}===")
        holder.tv_status.apply {
            when (listRequest[position].orderStatus) {
                1 -> {
                    this.text = this.context.getString(R.string.in_progress)
                    this.setTextColor(this.context.getColorStateList(R.color.orange_colour))
                }
                2 -> {
                    this.text = this.context.getString(R.string.packed)
                    this.setTextColor(this.context.getColorStateList(R.color.dark_green_colour))
                }
                3 -> {
                    this.text = this.context.getString(R.string.onWay)
                    this.setTextColor(this.context.getColorStateList(R.color.dark_green_colour))
                }

                4 -> {
                    this.text = this.context.getString(R.string.completed)
                    this.setTextColor(this.context.getColorStateList(R.color.dark_green_colour))
                }

            }
        }


        holder.tvDateTime.text = AppUtils.changeDateFormat(listRequest[position].createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat3,
            GlobalVariables.DATEFORMAT.DateTimeFormat2)

        holder.tv_name.text =listRequest[position].vendorDetail.shopName

        Glide.with(holder.itemView.context).load(listRequest[position].vendorDetail.shopLogo).placeholder(holder.itemView.context.getDrawable(R.drawable.place_holder)).into(holder.iv_profile)

        holder.itemView.apply {
            setOnClickListener {
                if(listRequest[position].orderStatus==4) clickInterFace.onClick(position)
                else clickInterFace.onClick(position)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_request, parent, false))
    }

    override fun getItemCount(): Int {
        return listRequest.size
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvOrder = view.findViewById<TextView>(R.id.tv_order)
        var tv_name = view.findViewById<TextView>(R.id.tv_name)
        var tvAddress = view.findViewById<TextView>(R.id.tvAddress)
        var tvDateTime = view.findViewById<TextView>(R.id.tvDateTime)
        var tv_status = view.findViewById<TextView>(R.id.tv_status)
        var iv_profile = view.findViewById<ImageView>(R.id.iv_profile)
    }

    fun onClickInterFace(click:OnClick)
    {
        this.clickInterFace = click
    }

    interface  OnClick{
        fun onClick(position: Int)
    }
}