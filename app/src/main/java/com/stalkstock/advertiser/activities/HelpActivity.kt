package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.stalkstock.R
import kotlinx.android.synthetic.main.activity_help.*
import kotlinx.android.synthetic.main.toolbar.*

class HelpActivity : AppCompatActivity(), View.OnClickListener {
    val mContext: Context =this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_help)
        tv_heading.text = "Help"
        iv_back.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }R.id.btn_ok->{
            startActivity(Intent(mContext, MainActivity::class.java))
            }
        }
    }
}