package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.commercial.view.activities.AddedProduct
import com.stalkstock.R
import com.stalkstock.commercial.view.model.BidingDetailResponse
import kotlinx.android.synthetic.main.request_product_adapter.view.*


class RequestProductAdapter(var list: ArrayList<AddedProduct.RequestProductData>,var orderList: ArrayList<BidingDetailResponse.OrderItem>) : RecyclerView.Adapter<RequestProductAdapter.MyViewHolder>() {

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
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {




        if(position==0)
        {
            holder.itemView.viewUp.visibility  = View.VISIBLE}
        else
        {
            holder.itemView.viewUp.visibility  = View.GONE
        }


/*        if (position==0){

            val type = Typeface.createFromAsset(context.getAssets(), "fonts/TitilliumWeb_Bold.ttf")

//        holder.itemView.tvCount.text=
            holder.tvCount.text="S. No."
            holder.tvCount.setTextColor(context.resources.getColor(R.color.black))
            holder.tvCount.setTypeface(type)


            holder.itemView.tvName.setText("Item Brand")
            holder.itemView.tvName.setTextColor(context.resources.getColor(R.color.black))
             holder.itemView.tvName.setTypeface(type)

            holder.itemView.tvType.setText("Item Name")
             holder.itemView.tvType.setTypeface(type)
            holder.itemView.tvType.setTextColor(context.resources.getColor(R.color.black))

            holder.itemView.tvQuantity.setText("Quantity")
             holder.itemView.tvQuantity.setTypeface(type)
            holder.itemView.tvQuantity.setTextColor(context.resources.getColor(R.color.black))

            holder.itemView.tvQuantityType.setText("U.O.M.")
            holder.itemView.tvQuantityType.setTextColor(context.resources.getColor(R.color.black))
            holder.itemView.tvQuantityType.setTypeface(type)

        }*/
//        else{
            var pos=position

            holder.itemView.tvType.text = orderList[pos].product.name
            holder.itemView.tvQuantity.text = orderList[pos].qty.toString()
            holder.itemView.tvQuantityType.text = orderList[pos].product.measurementName
//        holder.itemView.tvCount.text=
            holder.tvCount.text=pos.toString()+"."
//        }


        if(list[position].edit)
        {
            holder.itemView.ivEdit.visibility = View.VISIBLE
            holder.itemView.viewEdit.visibility = View.VISIBLE
            holder.itemView.rl_edit.visibility = View.VISIBLE
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
