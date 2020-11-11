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
import com.stalkstock.driver.models.CardModel
import kotlinx.android.synthetic.main.item_cards.view.*

class CardAdapter(var context: Context, var arrayList: ArrayList<CardModel>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var rememberPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cards, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivCard: ImageView = itemView.iv_card
        val tvCardName: TextView = itemView.tv
        val rbCardSelected: RadioButton = itemView.rb

        fun bind(pos: Int) {

            val cardModel = arrayList[pos]

            cardModel.cardImage?.let { ivCard.setImageResource(it) }
            tvCardName.text = cardModel.cardName

            if (cardModel.cardSelect) {
                rememberPosition = pos
                rbCardSelected.isChecked = cardModel.cardSelect
            }else{
                rbCardSelected.isChecked = cardModel.cardSelect
            }

            rbCardSelected.setOnClickListener {
                if (rememberPosition != null){
                    arrayList[rememberPosition!!].cardSelect = false
                }

                cardModel.cardSelect = true
                notifyDataSetChanged()
            }

        }
    }
}