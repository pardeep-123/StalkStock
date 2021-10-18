package com.stalkstock.vender.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.vender.Model.VendorOrderListResponse
import com.stalkstock.vender.ui.BottomnavigationScreen
import com.stalkstock.vender.ui.OrderDetails
import java.util.*

class PastAdapter(
    var context: BottomnavigationScreen,
    private val mOrderArrayList: ArrayList<VendorOrderListResponse.Body>
) : RecyclerView.Adapter<PastAdapter.ViewHolder>() {
    var inflater: LayoutInflater = LayoutInflater.from(context)
    var message4 = "Delivered"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.pastbtn, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val body = mOrderArrayList[position]
        if (body.paymentMethod==1)
        {
            holder.btnModeOfPayment.text = "Paid Online"
        }
        else{
            holder.btnModeOfPayment.text = "Paid Offline"
        }
        holder.txtName.text = body.firstName+" "+body.lastName
        holder.txtOrderNumber.text = body.orderNo
        holder.pastorder.setOnClickListener {
            val intent4 = Intent(context, OrderDetails::class.java)
            intent4.putExtra("key", message4)
            intent4.putExtra("orderId", body.id.toString())
            intent4.putExtra("show", "delivered_by")
            context.startActivity(intent4)
        }

    }
    override fun getItemCount(): Int {
        return mOrderArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pastorder: LinearLayout = itemView.findViewById(R.id.past_details)
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtOrderNumber: TextView = itemView.findViewById(R.id.txtOrderNumber)
        var btnModeOfPayment: Button = itemView.findViewById(R.id.btnModeOfPayment)

    }

}