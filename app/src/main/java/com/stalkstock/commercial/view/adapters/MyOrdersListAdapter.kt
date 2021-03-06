package com.stalkstock.commercial.view.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.commercial.view.model.ModelPojo
import com.stalkstock.R
import com.stalkstock.commercial.view.model.MyOrdersList
import com.stalkstock.consumer.model.OrderListModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_orderdeatils.*
import kotlinx.android.synthetic.main.item_orders.view.*
import kotlinx.android.synthetic.main.row_myorder.view.*
import java.text.SimpleDateFormat

class MyOrdersListAdapter(
    var context: Context,
    var list: ArrayList<OrderListModel.Body>,
    var listner: OnMyOrdersRecyclerViewItemClickListner
) : RecyclerView.Adapter<MyOrdersListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_orders, parent, false), listner
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.initalize(list, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View, var listner: OnMyOrdersRecyclerViewItemClickListner) :
        RecyclerView.ViewHolder(itemView) {
        val image = itemView.iv_brandImage
        val brandname = itemView.tv_brandName
        val city = itemView.tv_cityname
        val country = itemView.tv_countryname
        val description = itemView.tv_description
        val date = itemView.tv_dateOrders
        val time = itemView.tv_timeorders
        val price = itemView.tv_priceOrders
        val status = itemView.tv_statusValueorders

        fun initalize(list: ArrayList<OrderListModel.Body>, position: Int) {


            /* image.setImageResource(list[position].image)*/
            image.loadImage(list[position].orderVendor.shopLogo)
            brandname.text = list[position].orderVendor.shopName
            city.text = list[position].orderVendor.ShopAddress
//            country.text = list[position].countryname
            description.text = list[position].orderItems[0].product.name
            date.text = AppUtils.changeDateFormat(
                list[position].createdAt,
                GlobalVariables.DATEFORMAT.DateTimeFormat1,
                GlobalVariables.DATEFORMAT.DateTimeFormat2
            )
            /*  time.text = list[position].time*/
            price.text = "$" + list[position].total

            when (list[position].orderStatus) {
                0 -> {
                    status.text = context.getString(R.string.pending)
                }
                1 -> {
                    status.text = context.getString(R.string.in_progress)
                }
                2 -> {
                    status.text = context.getString(R.string.packed)
                }
                3 -> {
                    status.text = context.getString(R.string.onWay)
                }
                4 -> {
                    status.text = context.getString(R.string.completed)
                }
                5 -> {
                    status.text = context.getString(R.string.cancelled)
                }
                6 -> {
                    status.text = context.getString(R.string.rejected)
                }
                else -> {
                    status.text = context.getString(R.string.error_)
                }
            }

            status.setTextColor(
                ContextCompat.getColor(
                    context,
                    getStatusColor(list[position].orderStatus)
                )
            )


            /*if (list[position].orderStatus==4){
                status.text = "Delivered"
            }
            else{
                status.text = "Pending"
            }

*/
            itemView.setOnClickListener {
                listner.onMyOrdersItemClickListner(list, position)
            }

            if ((status.text as String?).equals("Pending", true)) {
                date.visibility = View.VISIBLE
                /* time.visibility=View.VISIBLE*/
                itemView.comma2.visibility = View.VISIBLE
            } else {
                date.visibility = View.GONE
                time.visibility = View.GONE
                itemView.comma2.visibility = View.GONE
            }

        }

        private fun getStatusColor(orderStatus: Int): Int {
            return when (orderStatus) {
                0 -> {
                    R.color.orange_colour
                }
                1 -> {
                    R.color.orange_colour
                }
                2 -> {
                    R.color.orange_colour
                }

                3 -> {
                    R.color.orange_colour
                }
                4 -> {
                    R.color.theme_green
                }
                5 -> {
                    R.color.red_dark_colour
                }
                6 -> {
                    R.color.red_dark_colour
                }
                else -> {
                    R.color.red_dark_colour
                }
            }
        }

    }

    interface OnMyOrdersRecyclerViewItemClickListner {
        fun onMyOrdersItemClickListner(list: ArrayList<OrderListModel.Body>, position: Int)
    }
}