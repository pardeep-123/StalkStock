package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.live.stalkstockcommercial.ui.paymnet.AddNewCard
import com.stalkstock.R
import kotlinx.android.synthetic.main.select_payment.*

class SelectPayment : AppCompatActivity() {


    var card = ""
     var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_payment)


        if(intent.hasExtra("card"))
        {
            card = intent.getStringExtra("card")!!
        }



        oneone.setOnClickListener {
            if(!type.equals("frist")){
                type = "frist"
                oneone.setImageResource(R.drawable.radio_fill)
                onetwo.setImageResource(R.drawable.radio_circle)
            }
        }
        onetwo.setOnClickListener {
            if(!type.equals("sec")){
                type = "sec"
                onetwo.setImageResource(R.drawable.radio_fill)
                oneone.setImageResource(R.drawable.radio_circle)
            }
        }
        ivBackPayment.setOnClickListener { onBackPressed() }
        btn_preview.setOnClickListener {
            startActivity(Intent(this, AddNewCard::class.java))

            /* if(card.isNotEmpty()){
             onBackPressed()}
         else
             {
                 startActivity(Intent(this,AddNewCard::class.java))
             }*/
        }
        btn_checkout.setOnClickListener { startActivity(Intent(this, PaymentStatus::class.java)) }
    }

}
