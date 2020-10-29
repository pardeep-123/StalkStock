package com.live.stalkstockcommercial.ui.view.fragments.account

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import com.stalkstock.advertiser.activities.EditBusinessProfileActivity
import kotlinx.android.synthetic.main.business_profile.*

class BusinessProfile : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.business_profile)

        ivBusiness.setOnClickListener { onBackPressed() }
        btn_edit.setOnClickListener { startActivity(Intent(this,EditBusinessProfileActivity::class.java)) }
    }

}
