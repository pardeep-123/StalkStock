package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.AddedProduct
import com.stalkstock.commercial.view.model.BidingDetailResponse
import kotlinx.android.synthetic.main.request_product_adapter.view.*

class RequestProductHomeAdapter(var list: ArrayList<AddedProduct.RequestProductData>, var orderList: ArrayList<BidingDetailResponse.OrderItem>) : RecyclerView.Adapter<RequestProductHomeAdapter.MyViewHolder>() {

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

    override fun onBindViewHolder(holder: RequestProductHomeAdapter.MyViewHolder, position: Int) {



        if(position==0)
        {
            holder.itemView.viewUp.visibility  = View.VISIBLE}
        else
        {
            holder.itemView.viewUp.visibility  = View.GONE
        }


        var pos=position

        holder.itemView.tvType.text = list[pos].name
        holder.itemView.tvQuantity.text =  list[pos].quantity
        holder.itemView.tvQuantityType.text =  list[pos].unit
//        holder.itemView.tvCount.text=
        holder.tvCount.text=pos.toString()+"."
//        }


            holder.itemView.rl_delete.setOnClickListener {
                Toast.makeText(context, "Request deleted successfully", Toast.LENGTH_SHORT).show()
                list.removeAt(pos)
                notifyItemRemoved(pos)
                notifyItemRangeChanged(pos,list.size)
            }

           holder.itemView.rl_edit.setOnClickListener{

           }


        if(list[position].edit)
        {
            holder.itemView.ivEdit.visibility = View.GONE
            holder.itemView.viewEdit.visibility = View.VISIBLE
            holder.itemView.rl_edit.visibility = View.GONE
            holder.itemView.rl_delete.visibility = View.VISIBLE

        }
        else
        {
            holder.itemView.ivEdit.visibility = View.GONE
            holder.itemView.rl_edit.visibility = View.GONE
            holder.itemView.viewEdit.visibility = View.GONE
            holder.itemView.rl_delete.visibility = View.GONE
        }

        if(list[position].delete)
        {
            holder.itemView.ivDelete.visibility = View.VISIBLE
            holder.itemView.viewDelete.visibility = View.VISIBLE

            holder.itemView.rl_edit.visibility = View.VISIBLE
            holder.itemView.rl_delete.visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.ivDelete.visibility = View.GONE
            holder.itemView.viewDelete.visibility = View.GONE
            holder.itemView.viewEdit.visibility = View.GONE
            holder.itemView.rl_delete.visibility = View.GONE
        }




        // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvCount=itemView.findViewById<TextView>(R.id.tvCount)


    }



}
