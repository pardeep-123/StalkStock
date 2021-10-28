package com.stalkstock.commercial.view.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.commercial.view.activities.AddedProduct
import com.stalkstock.R
import kotlinx.android.synthetic.main.product_unit_adapter.view.*

class AdapterProductUnit(var  mActivity : Activity, var listProductUnit: ArrayList<AddedProduct.ProductUnitData>) : RecyclerView.Adapter<AdapterProductUnit.MyViewHolder>() {

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


        holder.itemView.setOnClickListener {
            if (mActivity is AddedProduct)
            {
                holder.itemView.iv_rad.setImageResource(R.drawable.radio_fill)
                val addProduct = mActivity as AddedProduct
                addProduct.setSelectedMeasurement(position,listProductUnit[position])
            }
          /*  else if (context is EditProduct)
            {
                val addProduct = context as EditProduct
                addProduct.setSelectedMeasurement(position,listProductUnit[position])
            }*/
//            if (position == 0) {
//                holder.itemView.iv_rad.setImageResource(R.drawable.radio_circle)
//            }
//            else {
//                if (position.equals(getItemId(position))) {
//                    holder.itemView.iv_rad.setImageResource(R.drawable.radio_fill)
//                }
//                else {
//                    holder.itemView.iv_rad.setImageResource(R.drawable.radio_circle)
//                }
//
//            }
        }

        // holder.itemView.setOnClickListener { clickDoctor.clicked(position) }

        if(listProductUnit[position].name.isNotEmpty())
        {

            holder.itemView.tvTitle.visibility = View.GONE
            holder.itemView.tvUnit.text = listProductUnit[position].name
        }
        else
        {
            holder.itemView.tvTitle.visibility = View.GONE
        }



//        if(listProductUnit[position].selected)
//        {
//            holder.itemView.iv_rad.setImageResource(R.drawable.radio_fill)
//        }
//        else
//        {
//            holder.itemView.iv_rad.setImageResource(R.drawable.radio_circle)
//
//
//
//        }


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
