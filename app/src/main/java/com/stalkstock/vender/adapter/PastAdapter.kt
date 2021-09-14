package com.stalkstock.vender.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
    var inflater: LayoutInflater
    var message4 = "Delivered"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.pastbtn, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val body = mOrderArrayList[position]
        if (body.paymentMethod==1)
        {
            holder.btnModeOfPayment.setText("Paid Online")
        }
        else{
            holder.btnModeOfPayment.setText("Paid Offline")
        }
        holder.txtName.setText(body.firstName+" "+body.lastName)
        holder.txtOrderNumber.setText(body.orderNo.toString())
        holder.pastorder.setOnClickListener { /*Intent intent4 = new Intent(context, OrderDetails.class);
                    intent4.putExtra("valu",message4);
                    intent4.putExtra("show","delivered_by");
                    context.startActivity(intent4);*/
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
        var pastorder: LinearLayout
        var txtName: TextView
        var txtOrderNumber: TextView
        var txtRefund: TextView
        var txtRefundmoney: TextView
        var txtRefundComplete: TextView
        var btnModeOfPayment: Button

        init {
            pastorder = itemView.findViewById(R.id.past_details)
            btnModeOfPayment = itemView.findViewById(R.id.btnModeOfPayment)
            txtName = itemView.findViewById(R.id.txtName)
            txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber)
            txtRefund = itemView.findViewById(R.id.txtRefund)
            txtRefundComplete = itemView.findViewById(R.id.txtRefundComplete)
            txtRefundmoney = itemView.findViewById(R.id.txtRefundmoney)
        }
    }

    init {
        inflater = LayoutInflater.from(context)
    }
}