package com.stalkstock.consumer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.driver.models.UserCardBody
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateFormat1
import com.stalkstock.utils.others.GlobalVariables.DATEFORMAT.DateTimeFormat3

class UserCardAdapter(var list: MutableList<UserCardBody>) : RecyclerView.Adapter<UserCardAdapter.RecyclerViewHolder>() {

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

        holder.tvCardNumber.text= "**** ".repeat(3)+list[position].cardNumber.substring(list[position].cardNumber.length-4,list[position].cardNumber.length)
        holder.tvExpDate.text= "MM/YY "+(if(list[position].month.toString().length>1) list[position].month else "0"+list[position].month )+"/"+list[position].year.toString().substring(list[position].year.toString().length-2,list[position].year.toString().length)+"    CVV ***"
        holder.tvAddedOn.text= "Added on "+AppUtils.changeDateFormat(list[position].createdAt,DateTimeFormat3,DateFormat1)
        holder.ivDeleteCard.setOnClickListener { cardClicked.clicked(position,list[position].id) }
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