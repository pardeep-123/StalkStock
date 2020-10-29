package com.live.stalkstockcommercial.ui.product

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import kotlinx.android.synthetic.main.request_product_adapter.view.*

class RequestProductAdapter(var list: ArrayList<AddedProduct.RequestProductData>) : RecyclerView.Adapter<RequestProductAdapter.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.request_product_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        if(position==0)
        {holder.itemView.viewUp.visibility  = View.VISIBLE}
        else
        {
            holder.itemView.viewUp.visibility  = View.GONE
        }


        if(list[position].edit)
        {
            holder.itemView.ivEdit.visibility = View.VISIBLE
            holder.itemView.viewEdit.visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.ivEdit.visibility = View.GONE
            holder.itemView.viewEdit.visibility = View.GONE
        }

        if(list[position].delete)
        {
            holder.itemView.ivDelete.visibility = View.VISIBLE
            holder.itemView.viewDelete.visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.ivDelete.visibility = View.GONE
            holder.itemView.viewDelete.visibility = View.GONE
        }




        // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}
