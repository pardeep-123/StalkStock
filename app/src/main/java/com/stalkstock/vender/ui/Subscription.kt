package com.stalkstock.vender.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.stalkstock.R
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import com.stalkstock.vender.ui.SelectPaymentMethod
import android.widget.TextView

class Subscription : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscription)
        val imageView = findViewById<ImageView>(R.id.subscription_backarrow)
        val button = findViewById<Button>(R.id.upgrade_button)
        imageView.setOnClickListener { onBackPressed() }
        button.setOnClickListener {
            startActivity(
                Intent(
                    this@Subscription,
                    SelectPaymentMethod::class.java
                )
            )
        }
        val upgrade_button1 = findViewById<TextView>(R.id.upgrade_button1)
        upgrade_button1.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if(isTaskRoot){
            startActivity(Intent(this, BottomnavigationScreen::class.java).also {
                it.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP  or Intent.FLAG_ACTIVITY_NEW_TASK
            })

        }else{

            super.onBackPressed()
        }
    }


}