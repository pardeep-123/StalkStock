package com.stalkstock.advertiser.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import kotlinx.android.synthetic.main.delete_successfully_alert.*
import kotlinx.android.synthetic.main.paymentmethod_card.view.*

class PaymentMethodsAdapter(var mContext: Context?) : RecyclerView.Adapter<PaymentMethodsAdapter.MyViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.paymentmethod_card, parent, false)
        return MyViewHolder(itemView)


    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.layout_delete.setOnClickListener {
            reportuser()
        }

//        if(position==0){
//            holder.itemView.tvServices.text = context.getString(R.string.home_services_2)
//
//        }else if(position==1){
//            holder.itemView.tvServices.text = "Building & Construction"
//        }
//        else{
//            holder.itemView.tvServices.text = "Craftsman"
//
//
//        //  holder.rv?.layoutManager = LinearLayoutManager(holder.rv.context, LinearLayout.HORIZONTAL, false) as RecyclerView.LayoutManager?
//        var adapter = Adapter_Inner_HomeUser()
//        holder.rv?.adapter = adapter
//

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

      ///  val rv:RecyclerView=itemView.findViewById(R.id.recycle_row_user)

    }

    fun reportuser() {
        val customDialog: Dialog
        val customView = LayoutInflater.from(context).inflate(R.layout.delete_successfully_alert, null)
        // Build the dialog
         customDialog = Dialog(context)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setContentView(customView)
        customDialog.btn_yes.setOnClickListener { customDialog.dismiss() }
        customDialog.btn_no.setOnClickListener { customDialog.dismiss() }
        customDialog.show() }
}