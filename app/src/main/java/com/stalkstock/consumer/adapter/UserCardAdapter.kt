package com.stalkstock.consumer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.ManagePaymentsActivity
import com.stalkstock.driver.models.UserCardBody
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateFormat1
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateTimeFormat3
import kotlinx.android.synthetic.main.card_item.view.*


class UserCardAdapter(var list: MutableList<UserCardBody>,var managePaymentsActivity: ManagePaymentsActivity) : RecyclerView.Adapter<UserCardAdapter.RecyclerViewHolder>() {
    var selectedpos = -1
    lateinit var selectedAdd: CheckBox

    class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvCardNumber: TextView = view.findViewById(R.id.tvCardNumber)
        var tvExpDate: TextView = view.findViewById(R.id.tvExpDate)
        var tvAddedOn: TextView = view.findViewById(R.id.tvAddedOn)
        var ivDeleteCard: ImageView = view.findViewById(R.id.ivDeleteCard)
    }

    lateinit var cardClicked :  CardClicked

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return RecyclerViewHolder(v) }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val cbDefault: CheckBox = holder.itemView.cbDefaultCard
        holder.tvCardNumber.text= "**** ".repeat(3)+list[position].cardNumber.substring(list[position].cardNumber.length-4,list[position].cardNumber.length)
        holder.tvExpDate.text= "MM/YY "+(if(list[position].month.toString().length>1) list[position].month else "0"+list[position].month )+"/"+list[position].year.toString().substring(list[position].year.toString().length-2,list[position].year.toString().length)+"    CVV ***"
        holder.tvAddedOn.text= "Added on "+AppUtils.changeDateFormat(list[position].createdAt,DateTimeFormat3,DateFormat1)
        holder.ivDeleteCard.setOnClickListener { cardClicked.clicked(position,list[position].id) }

        if(list[position].isDefault==1){
            holder.itemView.cbDefaultCard.isChecked = true
        }else{
            holder.itemView.cbDefaultCard.isChecked = false
        }
        holder.itemView.rlMain.setOnClickListener {
            if (selectedpos < 0) {
                selectedpos = position
                selectedAdd = cbDefault
                cbDefault.isChecked = true
                managePaymentsActivity.setDefaultCardApi(list[position].id.toString())
            } else {
                if (holder.itemView.cbDefaultCard.isChecked) {
                    selectedpos = -1
                    holder.itemView.cbDefaultCard.isChecked = false
                    managePaymentsActivity.setDefaultCardApi(list[position].id.toString())
                } else {
                    selectedAdd.isChecked = false
                    cbDefault.isChecked = true
                    selectedpos = position
                    selectedAdd = cbDefault
                    managePaymentsActivity.setDefaultCardApi(list[position].id.toString())
                }
            }
        }

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun onPerformClick( cardClicked: CardClicked) {
        this.cardClicked = cardClicked
    }

    interface CardClicked{
        fun clicked(position: Int, id: Int)
    }

}