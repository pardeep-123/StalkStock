package com.live.stalkstockcommercial.ui.paymnet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.add_new_card.*

class AddNewCard : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_card)

        ivBackAddNewCard.setOnClickListener { onBackPressed() }
        btn_save.setOnClickListener {

            val intent = Intent(this,SelectPayment::class.java)
            intent.putExtra("card","yes")
            startActivity(intent) }
    }
}
