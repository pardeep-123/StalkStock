package com.live.stalkstockcommercial.ui.view.fragments.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.fragments.HomeFragment
import com.stalkstock.commercial.view.fragments.home.HomeFragmentCommercial

class HomeAdapter(var list: ArrayList<HomeFragmentCommercial.HomeData>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.home_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




       // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }


        holder.itemView.setOnClickListener {

            context.startActivity(Intent(context,RequestDetail::class.java))
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}
