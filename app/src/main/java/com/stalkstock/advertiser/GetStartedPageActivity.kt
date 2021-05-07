package com.stalkstock.advertiser

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.consumer.activities.SelectuserActivity
import com.stalkstock.utils.TermsConditionActivity
import com.stalkstock.vender.ui.LoginScreen
import kotlinx.android.synthetic.main.activity_get_started_page.*

class GetStartedPageActivity : AppCompatActivity() {



    var click=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_started_page)



        tv_not_now.setOnClickListener { onBackPressed() }
        btn_let_go.setOnClickListener {

            if(MyApplication.instance.getString("usertype").equals("1")){
                startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                finishAffinity()
            }else if(MyApplication.instance.getString("usertype").equals("2")){
                startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                finishAffinity()
            }else if(MyApplication.instance.getString("usertype").equals("3")){
                startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                finishAffinity()
            }else if(MyApplication.instance.getString("usertype").equals("4")){
                startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                finishAffinity()
            }/*else if(MyApplication.instance.getString("usertype").equals("4")){
                startActivity(Intent(this@GetStartedPageActivity, LoginScreen::class.java))
                finishAffinity()
            }*/else if(MyApplication.instance.getString("usertype").equals("5")){
                startActivity(Intent(this@GetStartedPageActivity, LoginActivity::class.java))
                finishAffinity()
            }

            }

        tv_termss.setOnClickListener {
            if (click == 0) {
                click = 1
                val intent = Intent(
                    this@GetStartedPageActivity,
                    TermsConditionActivity::class.java
                )
                startActivity(intent)
            }
        }


    }

    override fun onResume() {
        super.onResume()
        click = 0
    }
    override fun onBackPressed() {
        //super.onBackPressed()
        startActivity(Intent(this@GetStartedPageActivity, SelectuserActivity::class.java))
        finishAffinity()
    }
}
