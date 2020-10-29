package com.live.stalkstockcommercial.ui.view.adapters.myorders

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.live.stalkstockcommercial.ui.models.ModelPojo
import com.stalkstock.R
import kotlinx.android.synthetic.main.item_orders.view.*

class MyOrdersListAdapter(var context: Context, var list:ArrayList<ModelPojo.MyOrdersListModel>, var listner: OnMyOrdersRecyclerViewItemClickListner):RecyclerView.Adapter<MyOrdersListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_orders, parent, false), listner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initalize(list, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner  class  MyViewHolder(itemView:View, var listner: OnMyOrdersRecyclerViewItemClickListner):RecyclerView.ViewHolder(itemView){
        val image = itemView.iv_brandImage
        val brandname = itemView.tv_brandName
        val city = itemView.tv_cityname
        val country = itemView.tv_countryname
        val description = itemView.tv_description
        val date = itemView.tv_dateOrders
        val time = itemView.tv_timeorders
        val price = itemView.tv_priceOrders
        val status = itemView.tv_statusValueorders

        fun initalize(list: ArrayList<ModelPojo.MyOrdersListModel>, position: Int){

            image.setImageResource(list.get(position).image)
            brandname.text = list.get(position).brandName
            city.text = list.get(position).cityname
            country.text = list.get(position).countryname
            description.text = list.get(position).description
            date.text = list.get(position).date
            time.text = list.get(position).time
            price.text = list.get(position).price
            status.text = list.get(position).status

            itemView.setOnClickListener {
                listner!!.onMyOrdersItemClickListner(list, position)
            }

            if((status.text as String?).equals("Pending",true)){
                status.setTextColor(Color.parseColor("#fb862e"))
                date.visibility=  View.VISIBLE
                time.visibility=View.VISIBLE
                itemView.comma2.visibility= View.VISIBLE
            }else{
                status.setTextColor(Color.parseColor("#459972"))
                date.visibility=  View.GONE
                time.visibility=View.GONE
                itemView.comma2.visibility= View.GONE
            }

        }

    }

    interface OnMyOrdersRecyclerViewItemClickListner{
        fun onMyOrdersItemClickListner(list: ArrayList<ModelPojo.MyOrdersListModel>, position: Int)
    }
}