package com.live.stalkstockcommercial.ui.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import kotlinx.android.synthetic.main.add_thanks.*

class AddThanks : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_thanks)


        iv_backArrow.setOnClickListener { onBackPressed() }
        btnGo.setOnClickListener { onBackPressed() }

    }

}
