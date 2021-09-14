package com.stalkstock.vender.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.vender.Model.OrderDetailVendorResponse
import com.stalkstock.vender.ui.OrderDetails
import kotlinx.android.synthetic.main.row_myordervendordetailproduct.view.*

class MyVendorOrderProductAdapter(
    var context: OrderDetails,
    var mOrderArrayList: List<OrderDetailVendorResponse.Body.OrderItem>
) : RecyclerView.Adapter<MyVendorOrderProductAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_myordervendordetailproduct, parent, false)
        return RecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        holder.itemView.name_text.setText(mOrderArrayList[position].product.name)
        holder.itemView.tvQuantity.setText("Quantity : "+mOrderArrayList[position].qty)

    }

    override fun getItemCount(): Int {
        return mOrderArrayList.size
    }
    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view)

}