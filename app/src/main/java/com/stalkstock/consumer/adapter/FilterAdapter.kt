package com.stalkstock.consumer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import kotlinx.android.synthetic.main.row_filter.view.*
import com.stalkstock.consumer.activities.FilterActivity


class FilterAdapter(var list: ArrayList<FilterActivity.FilterData>) : RecyclerView.Adapter<FilterAdapter.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.row_filter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }
        holder.itemView.apply {
            if (list[position].isBoolen)
            {
                tv_item.background = resources.getDrawable(R.drawable.strokegreen)
                tv_item.setTextColor(resources.getColor(R.color.green_colour))
            }else{
                tv_item.background = resources.getDrawable(R.drawable.strokegray)
                tv_item.setTextColor(resources.getColor(R.color.dark_gray))
            }
            setOnClickListener {
                if(list[position].isBoolen)
                    list[position].isBoolen = false
                else
                list[position].isBoolen = true
                notifyDataSetChanged()
            }
            tv_item.text = list[position].name
        }





    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}