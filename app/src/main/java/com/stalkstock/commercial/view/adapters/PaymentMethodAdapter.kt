package com.live.stalkstockcommercial.ui.paymnet

import android.app.Dialog
import android.content.Context
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import kotlinx.android.synthetic.main.payment_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class PaymentMethodAdapter(var list: ArrayList<ManagePayment.PaymentMethodData>) : RecyclerView.Adapter<PaymentMethodAdapter.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.payment_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.itemView.tvName.text =list[position].type
        holder.itemView.tvNumber.text =list[position].cardNumber
        holder.itemView.tvDatePattern.text =list[position].monthData
        holder.itemView.tvDate.text =list[position].date
        holder.itemView.ivIcon.setImageDrawable(list[position].icon)

        if(list[position].default)
        {
        holder.itemView.ivSelected.setImageDrawable(context!!.resources.getDrawable(R.drawable.radio_fill))


        }
else
        {
            holder.itemView.ivSelected.setImageDrawable(context!!.resources.getDrawable(R.drawable.radio_circle))
        }


       //  holder.itemView.setOnClickListener { clickDoctor.clicked(position) }

        holder.itemView.ivDelete.setOnClickListener { deleteClicked() }




    }

    private fun deleteClicked() {

        val dialogSuccessful = Dialog(context, R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.delete_successfully_alertaddress)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull<Window>(dialogSuccessful.window).setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)

        val btn_yes = dialogSuccessful.findViewById<Button>(R.id.btn_yes)
        val tvMsg = dialogSuccessful.findViewById<TextView>(R.id.tv_msg)
        val btn_no = dialogSuccessful.findViewById<Button>(R.id.btn_no)


        tvMsg.text = "Are you sure you want to remove this payment Method?"


        btn_yes.setOnClickListener { dialogSuccessful.dismiss() }

        btn_no.setOnClickListener { dialogSuccessful.dismiss() }

        dialogSuccessful.show()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}