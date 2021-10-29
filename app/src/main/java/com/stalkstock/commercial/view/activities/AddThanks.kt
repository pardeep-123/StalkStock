package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.add_thanks.*

class AddThanks : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_thanks)

        tvRequestId.text = "Request Id:"+" "+intent.getStringExtra("requestNo")

        iv_backArrow.setOnClickListener { onBackPressed() }
        btnGo.setOnClickListener {
            startActivity(Intent(this, MainCommercialActivity::class.java))
            finishAffinity()
        }

    }

}
