package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.net.Status
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.MainCommercialActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.SelectuserActivity
import com.stalkstock.consumer.activities.SignupConsumerActivity
import com.stalkstock.driver.HomeActivity
import com.stalkstock.response_models.user_response.user_login.UserLoginResponse
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.vender.ui.BottomnavigationScreen
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.net.RestObservable
import com.tamam.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener,Observer<RestObservable> {

    val viewModel:HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    val mContext:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

                emailEdittext.text.toString()
                passwordEdittext.text.toString()
                SetValidation()

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
        if(MyApplication.instance.getString("usertype").equals("1")){
            startActivity(Intent(mContext, MainActivity::class.java))
            finishAffinity()
        }else if(MyApplication.instance.getString("usertype").equals("3")){
            startActivity(Intent(mContext, MainConsumerActivity::class.java))
            finishAffinity()
        }else if(MyApplication.instance.getString("usertype").equals("2")){
            startActivity(Intent(mContext, MainCommercialActivity::class.java))
            finishAffinity()
        }else if(MyApplication.instance.getString("usertype").equals("4")){
            startActivity(Intent(mContext, BottomnavigationScreen::class.java))
            finishAffinity()
        }else if(MyApplication.instance.getString("usertype").equals("5")){
            startActivity(Intent(mContext, HomeActivity::class.java))
            finishAffinity()
        }
    }


    fun goingToSignUp(){
        /*
        * 1 =advser
        * 2=comercial
        * 4=vender
        *
        * */
        if(MyApplication.instance.getString("usertype").equals("1")){
            startActivity(Intent(mContext, SignupActivity::class.java))
        }else if(MyApplication.instance.getString("usertype").equals("3")){
            startActivity(Intent(mContext, SignupConsumerActivity::class.java))
        }else if(MyApplication.instance.getString("usertype").equals("2")){
            startActivity(Intent(mContext, SignupActivity::class.java))
        }else if(MyApplication.instance.getString("usertype").equals("4")){
           startActivity(Intent(mContext, SignupActivity::class.java))
         //  startActivity(Intent(mContext, SignUp::class.java))
        }else if(MyApplication.instance.getString("usertype").equals("5")){
            startActivity(Intent(mContext, com.stalkstock.driver.SignupActivity::class.java))
        }
    }


    fun SetValidation()  {
        // Check for a valid email address.
        if (emailEdittext.getText().toString().isEmpty()) {
            emailEdittext.requestFocus()
            emailEdittext.setError(resources.getString(R.string.please_enter_email))

        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.getText().toString()).matches()) {
            emailEdittext.requestFocus()
            emailEdittext.setError(resources.getString(R.string.please_enter_valid_email))

        }
        // Check for a valid password.
        else if (passwordEdittext.getText().toString().isEmpty()) {
            passwordEdittext.requestFocus()
            passwordEdittext.setError(resources.getString(R.string.please_enter_password))
        }  else {
            /***
             * Data willl set here to sent in api call
             */
            val hashMap = HashMap<String,String>()
            hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.device_type] = GlobalVariables.PARAM.android_device_type
            hashMap[GlobalVariables.PARAM.device_token] = getPrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, "666666")
            //Api will call here

            viewModel.postuserloginApi(this, true,hashMap)
            viewModel.homeResponse.observe(this, this)

//            if(!getPrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, "").isNullOrEmpty()){
//                viewModel.postuserloginApi(this, true,hashMap)
//                viewModel.homeResponse.observe(this, this)
//
//            }else{
//                Toast.makeText(this, "Device token is required ", Toast.LENGTH_SHORT).show()
//            }


        }

    }

    override fun onChanged(t: RestObservable?) {
        when(t?.status){
            Status.SUCCESS ->{
                if (t.data is UserLoginResponse){
                    val response = t.data.body
                    goingToHome()
                }

            }
        }

    }
}