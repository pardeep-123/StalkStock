package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.WindowManager
import com.stalkstock.R
import com.stalkstock.consumer.activities.SelectuserActivity

class SplashActivity : AppCompatActivity() {
    val mContext : Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

        val countDownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                startActivity(Intent(mContext, SelectuserActivity::class.java))
                finishAffinity()
            }
        }.start()
    }
}