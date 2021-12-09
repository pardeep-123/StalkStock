package com.stalkstock.vender.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.VendorOrderListResponse
import com.stalkstock.vender.ui.OrderDetails
import com.stalkstock.utils.others.AppUtils.changeDateFormat
import kotlinx.android.synthetic.main.neworderlist.view.*

class NewOrderAdapter(
    var context: Context?,
    var text: String?,
    var mOrderArrayList: ArrayList<VendorOrderListResponse.Body>
) :
    RecyclerView.Adapter<NewOrderAdapter.ViewHolder>() {
    var inflater: LayoutInflater

    //String message="New Orders";
    //    String message2="In Progress";
    //
    //    String message3="Ready for Pickup";
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.neworderlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvName.text = mOrderArrayList[position].firstName+" "+mOrderArrayList[position].lastName
        holder.itemView.tvOrderNumber.text = mOrderArrayList[position].orderNo
        holder.itemView.tvDate.text = changeDateFormat(
            mOrderArrayList[position].updatedAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat1,
            GlobalVariables.DATEFORMAT.DateFormat1
        )
        holder.itemView.tvTime.text = changeDateFormat(
            mOrderArrayList[position].updatedAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat1,
            GlobalVariables.DATEFORMAT.TimeFormat1
        )
    }
    override fun getItemCount(): Int {
        return mOrderArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var orderlistthree: LinearLayout

        init {
            orderlistthree = itemView.findViewById(R.id.orderlistthree)
            orderlistthree.setOnClickListener {
                val intent4 = Intent(context, OrderDetails::class.java)
                /*if (text.equals("New Orders")) {
                    intent4.putExtra("key", "New")
                } else if (text.equals("In Progress")) {
                    intent4.putExtra("value", "In Progress")
                } else if (text.equals("Ready for Pickup")) {
                    intent4.putExtra("val", "Ready For Pickup")
                } else {
                }*/
                //                    intent4.putExtra("value",message2);
//                    intent4.putExtra("val",message3);
                if (text.equals("New Orders")) {
                    intent4.putExtra("key", "New")
                } else if (text.equals("In Progress")) {
                    intent4.putExtra("key", "In Progress")
                } else if (text.equals("Ready for Pickup")) {
                    intent4.putExtra("key", "Ready For Pickup")
                } else {
                }
                intent4.putExtra("orderId",mOrderArrayList[adapterPosition].id.toString())
                context!!.startActivity(intent4)
            }
        }
    }

    init {
        this.text = text
        inflater = LayoutInflater.from(context)
    }
}