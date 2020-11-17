package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.MainCommercialActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.SelectuserActivity
import com.stalkstock.consumer.activities.SignupConsumerActivity
import com.stalkstock.driver.HomeActivity
import com.stalkstock.utils.others.AppController
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val mContext:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_forgot_password.setOnClickListener(this)
        tv_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        signup.setOnClickListener(this)
        back.setOnClickListener(this)



        iv_fb.setOnClickListener {
            goingToHome()


        }
        iv_gmail.setOnClickListener {
            goingToHome()


        }

        iv_twitter.setOnClickListener {
            goingToHome()


        }
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.tv_forgot_password->{
                startActivity(Intent(mContext, ForgotPasswordActivity::class.java))
            }
            R.id.back ->{
                // super.onBackPressed();
                startActivity(Intent(this@LoginActivity, SelectuserActivity::class.java))
                finishAffinity()
            }

            R.id.tv_signup->{
                goingToSignUp()

            }


            R.id.signup->{
                goingToSignUp()

            }


            R.id.btn_signin->{
                goingToHome()
            }


        }
    }


    override fun onBackPressed() {
      //  super.onBackPressed()
        // super.onBackPressed();
        startActivity(Intent(this@LoginActivity, SelectuserActivity::class.java))
        finishAffinity()
    }
    fun goingToHome(){
        if(AppController.getInstance().getString("usertype").equals("1")){
            startActivity(Intent(mContext, MainActivity::class.java))
            finishAffinity()
        }else if(AppController.getInstance().getString("usertype").equals("3")){
            startActivity(Intent(mContext, MainConsumerActivity::class.java))
            finishAffinity()
        }else if(AppController.getInstance().getString("usertype").equals("2")){
            startActivity(Intent(mContext, MainCommercialActivity::class.java))
            finishAffinity()
        }else if(AppController.getInstance().getString("usertype").equals("5")){
            startActivity(Intent(mContext, HomeActivity::class.java))
            finishAffinity()
        }
    }


    fun goingToSignUp(){
        if(AppController.getInstance().getString("usertype").equals("1")){
            startActivity(Intent(mContext, SignupActivity::class.java))
        }else if(AppController.getInstance().getString("usertype").equals("3")){
            startActivity(Intent(mContext, SignupConsumerActivity::class.java))
        }else if(AppController.getInstance().getString("usertype").equals("2")){
            startActivity(Intent(mContext, SignupActivity::class.java))
        }else if(AppController.getInstance().getString("usertype").equals("5")){
            startActivity(Intent(mContext, com.stalkstock.driver.SignupActivity::class.java))
        }
    }
}