package com.stalkstock.driver.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.driver.models.BankBody
import kotlinx.android.synthetic.main.item_cards.view.*

class CardAdapter(var context: Context, var list: List<BankBody>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var rememberPosition: Int? = null

    var defaultCardId = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cards, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivCard: ImageView = itemView.iv_card
        val tvCardName: TextView = itemView.tv
        val rbCardSelected: RadioButton = itemView.rb

        fun bind(pos: Int) {

            val cardModel = list[pos]

            //cardModel.cardImage?.let { ivCard.setImageResource(it) }
            tvCardName.text= "**** ".repeat(3)+cardModel.bankAccount.substring(cardModel.bankAccount.length-4,cardModel.bankAccount.length)

            if (cardModel.isDefault==1) {
                rememberPosition = pos
                rbCardSelected.isChecked = true
                defaultCardId = cardModel.id.toString()
            }
            else{
                rbCardSelected.isChecked = false
            }
            rbCardSelected.setOnClickListener {
                if (list[pos].isDefault==0){
                    (list.indices).map { list[it].isDefault = 0 }
                    list[pos].isDefault = 1
                    defaultCardId = list[pos].id.toString()


                    notifyDataSetChanged()
                }
            }
        }
    }
}