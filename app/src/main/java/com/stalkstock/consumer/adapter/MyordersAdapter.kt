package com.stalkstock.consumer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.OrderdeatilsActivity
import com.stalkstock.consumer.model.OrderListModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateTimeFormat
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateTimeFormat1
import com.tamam.utils.others.AppUtils.changeDateFormat
import kotlinx.android.synthetic.main.row_myorder.view.*

class MyordersAdapter(
    var context: MainConsumerActivity,
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
        holder.itemView.kfc.setText(mOrderArrayList[position].orderVendor.shopName)
        holder.itemView.tvLocation.setText(mOrderArrayList[position].orderVendor.ShopAddress)
        var mProductName = ""
        for (model in mOrderArrayList[position].orderItems)
        {
            mProductName = mProductName+","+model.product.name
        }
        if (mProductName.startsWith(","))
            holder.itemView.starCount.setText(mProductName.substring(1))
        holder.itemView.tvDate.setText(changeDateFormat(mOrderArrayList[position].updatedAt,DateTimeFormat1,DateTimeFormat))
        holder.itemView.tvPrice.setText("$"+mOrderArrayList[position].total)
        if (mOrderArrayList[position].orderStatus == 0)
            holder.itemView.tvStatus.setText("Pending")
        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderdeatilsActivity::class.java)
            intent.putExtra("orderId",mOrderArrayList[position].id.toString())
            context.startActivity(intent)
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