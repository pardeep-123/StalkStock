package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.payment_status.*

class PaymentStatus : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.payment_status)
        btn_view.setOnClickListener {
            startActivity(Intent(this,MainCommercialActivity::class.java))
            finishAffinity()
        }
        back_img.setOnClickListener {
            startActivity(Intent(this,MainCommercialActivity::class.java))
            finishAffinity()
        }
    }

}
