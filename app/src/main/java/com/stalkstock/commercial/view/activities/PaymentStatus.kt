package com.live.stalkstockcommercial.ui.paymnet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.MainCommercialActivity
import kotlinx.android.synthetic.main.payment_status.*

class PaymentStatus : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_status)
        btn_view.setOnClickListener {
            startActivity(Intent(this,MainCommercialActivity::class.java))
            finishAffinity()
        }
    }

}
