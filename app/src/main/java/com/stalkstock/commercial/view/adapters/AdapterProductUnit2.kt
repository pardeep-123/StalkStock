package com.live.stalkstockcommercial.ui.view.fragments.home

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.ProductUnitData
import com.stalkstock.vender.ui.SelectCategory
import com.stalkstock.vender.ui.EditProduct
import kotlinx.android.synthetic.main.product_unit_adapter.view.*

class AdapterProductUnit2(var mActivity : Activity, var listProductUnit: ArrayList<ProductUnitData>) : RecyclerView.Adapter<AdapterProductUnit2.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.product_unit_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listProductUnit.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }

        if(listProductUnit[position].title.toString().isNotEmpty())
        {
         holder.itemView.tvTitle.text =    listProductUnit[position].title
            holder.itemView.tvTitle.visibility = View.VISIBLE
        }
        else
        {
            holder.itemView.tvTitle.visibility = View.GONE
        }

        holder.itemView.tvUnit.text = listProductUnit[position].unit

        if(listProductUnit[position].selected)
        {
            holder.itemView.iv_rad.setImageResource(R.drawable.radio_fill)
        }
        else
        {
            holder.itemView.iv_rad.setImageResource(R.drawable.radio_circle)
        }

        holder.itemView.setOnClickListener {
            if (mActivity is SelectCategory)
            {
                val SelectCategory = mActivity as SelectCategory
                SelectCategory.setSelectedMeasurement(position,listProductUnit[position])
            }
            else if (mActivity is EditProduct)
            {
                val SelectCategory = mActivity as EditProduct
                SelectCategory.setSelectedMeasurement(position,listProductUnit[position])
            }

        }
//
//       if(listProductUnit[position].selected)
//        {
//            holder.itemView.tvUnit.setCompoundDrawablesWithIntrinsicBounds(null,
//                null,context.resources.getDrawable(R.drawable.radio_fill),null)
//        }
//        else
//        {
//            holder.itemView.tvUnit.setCompoundDrawablesWithIntrinsicBounds(null,
//                null,context.resources.getDrawable(R.drawable.radio_circle),null)
//
//        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}
