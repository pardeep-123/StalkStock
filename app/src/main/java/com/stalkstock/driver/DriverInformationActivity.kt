package com.stalkstock.driver

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.stalkstock.R
import com.stalkstock.advertiser.activities.PreviewActivity
import kotlinx.android.synthetic.main.activity_driver_information.*
import kotlinx.android.synthetic.main.toolbar.*

class DriverInformationActivity : AppCompatActivity() {
    val mContext:Context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_driver_information)
        tv_heading.text = "Driver Information"
        iv_back.setOnClickListener{
            finish()
        }
        btn_editInfo.setOnClickListener {
            val intent = Intent(mContext, EditDriverInfoActivity::class.java)
            startActivity(intent)
        }
    }
}