package com.stalkstock.consumer.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.OrderdeatilsActivity
import com.stalkstock.consumer.model.OrderListModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateTimeFormat
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateTimeFormat1
import com.stalkstock.utils.others.AppUtils.changeDateFormat
import kotlinx.android.synthetic.main.row_myorder.view.*

class MyordersAdapter(
    var context: Context,
    var mOrderArrayList: ArrayList<OrderListModel.Body>
) : RecyclerView.Adapter<MyordersAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_myorder, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.itemView.img.loadImage(mOrderArrayList[position].orderVendor.shopLogo)
        holder.itemView.kfc.text = mOrderArrayList[position].orderVendor.shopName
        holder.itemView.tvLocation.text = mOrderArrayList[position].orderVendor.ShopAddress
        var mProductName = ""
        for (model in mOrderArrayList[position].orderItems)
        {
            mProductName = mProductName+","+model.product.name
        }
        if (mProductName.startsWith(","))
            holder.itemView.starCount.text = mProductName.substring(1)
        holder.itemView.tvDate.text = changeDateFormat(mOrderArrayList[position].updatedAt,DateTimeFormat1,DateTimeFormat)
        holder.itemView.tvPrice.text = "$"+mOrderArrayList[position].total

        when(mOrderArrayList[position].orderStatus)
        {
            0->{holder.itemView.tvStatus.text = "Pending"
            }
            1->{holder.itemView.tvStatus.text = "In Progress"}
            2->{holder.itemView.tvStatus.text = "Packed"}
            3->{holder.itemView.tvStatus.text = "On the way"}
            4->{holder.itemView.tvStatus.text = "Completed"}
            5->{holder.itemView.tvStatus.text = "Cancelled"}
            6->{holder.itemView.tvStatus.text = "Rejected"}
            else->{holder.itemView.tvStatus.text = "Error"}
        }
        holder.itemView.tvStatus.setTextColor(ContextCompat.getColor(holder.itemView.context,getStatusColor(mOrderArrayList[position].orderStatus)))

        if (mOrderArrayList[position].orderStatus == 0)
            holder.itemView.tvStatus.text = "Pending"
        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderdeatilsActivity::class.java)
            intent.putExtra("orderId",mOrderArrayList[position].id.toString())
            context.startActivity(intent)
        }
    }

    private fun getStatusColor(orderStatus: Int): Int {
        return when(orderStatus) {
            0->{
                R.color.orange_colour
            }
            1->{
                R.color.orange_colour
            }2->{
                R.color.orange_colour
            }

            3->{
                R.color.orange_colour
            }
            4->{
                R.color.theme_green
            }
            5->{
                R.color.red_dark_colour
            }
            6->{
                R.color.red_dark_colour
            }
            else->{
                R.color.red_dark_colour
            }
        }
    }

    override fun getItemCount(): Int {
        return mOrderArrayList.size
    }

    fun notifyData(mOrderArrayList: ArrayList<OrderListModel.Body>) {
        this.mOrderArrayList = mOrderArrayList
    }
    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

}