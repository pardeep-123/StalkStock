package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.MainCommercialActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.SignupConsumerActivity
import com.stalkstock.utils.others.AppController
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val mContext:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_forgot_password.setOnClickListener(this)
        tv_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        signup.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.tv_forgot_password->{
                startActivity(Intent(mContext, ForgotPasswordActivity::class.java))
            }


            R.id.tv_signup->{
               if(AppController.getInstance().getString("usertype").equals("1")){
                   startActivity(Intent(mContext, SignupActivity::class.java))
               }else if(AppController.getInstance().getString("usertype").equals("3")){
                   startActivity(Intent(mContext, SignupConsumerActivity::class.java))
               }else if(AppController.getInstance().getString("usertype").equals("2")){
                   startActivity(Intent(mContext, SignupActivity::class.java))
               }

            }


            R.id.signup->{

                if(AppController.getInstance().getString("usertype").equals("1")){
                    startActivity(Intent(mContext, SignupActivity::class.java))
                }else if(AppController.getInstance().getString("usertype").equals("3")){
                    startActivity(Intent(mContext, SignupConsumerActivity::class.java))
                }else if(AppController.getInstance().getString("usertype").equals("2")){
                    startActivity(Intent(mContext, SignupActivity::class.java))
                }
            }


            R.id.btn_signin->{
                if(AppController.getInstance().getString("usertype").equals("1")){
                    startActivity(Intent(mContext, MainActivity::class.java))
                    finishAffinity()
                }else if(AppController.getInstance().getString("usertype").equals("3")){
                    startActivity(Intent(mContext, MainConsumerActivity::class.java))
                    finishAffinity()
                }else if(AppController.getInstance().getString("usertype").equals("2")){
                    startActivity(Intent(mContext, MainCommercialActivity::class.java))
                    finishAffinity()
                }
            }


        }
    }
}