package com.stalkstock.driver.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import kotlinx.android.synthetic.main.row_request.view.*

 class RequestAdapter(
    var type :String, var click :OnClick
) : RecyclerView.Adapter<RequestAdapter.AdsHolder>() {

    override fun onBindViewHolder(holder: AdsHolder, position: Int) {


        holder.itemView.apply {
            setOnClickListener {
                if (type == "2")
                click.onClick("2")
                else
                click.onClick("1")
            }
            if (type == "2"){
                tv_status.text = resources.getString(R.string.completed)
                tv_status.setTextColor( resources.getColor(R.color.dark_green_colour))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdsHolder {
        return AdsHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_request, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return if (type == "2")
            2
        else
            1
    }

    inner class AdsHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    interface  OnClick{
        fun onClick(type:String)
    }
}