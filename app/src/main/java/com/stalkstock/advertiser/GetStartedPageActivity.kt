package com.stalkstock.advertiser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.consumer.activities.SelectuserActivity
import com.stalkstock.utils.TermsConditionActivity
import kotlinx.android.synthetic.main.activity_get_started_page.*

class GetStartedPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started_page)
        tv_not_now.setOnClickListener { onBackPressed() }
        btn_let_go.setOnClickListener {

            when {
                MyApplication.instance.getString("usertype").equals("1") -> {
                    startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                    finishAffinity()
                }
                MyApplication.instance.getString("usertype").equals("2") -> {
                    startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                    finishAffinity()
                }
                MyApplication.instance.getString("usertype").equals("3") -> {
                    startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                    finishAffinity()
                }
                MyApplication.instance.getString("usertype").equals("4") -> {
                    startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                    finishAffinity()
                }
                MyApplication.instance.getString("usertype").equals("5") -> {
                    startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                    finishAffinity()
                } } }

        tv_termss.setOnClickListener {

                val intent = Intent(
                    this@GetStartedPageActivity,
                    TermsConditionActivity::class.java
                )
                startActivity(intent)

        }


    }


    override fun onBackPressed() {
        startActivity(Intent(this@GetStartedPageActivity, SelectuserActivity::class.java))
        finishAffinity()
    }
}
