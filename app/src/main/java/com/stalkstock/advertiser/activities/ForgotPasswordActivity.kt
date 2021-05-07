package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.net.Status
import com.stalkstock.R
import com.stalkstock.response_models.common.forgot.ForgotPasswordResponse
import com.stalkstock.response_models.user_response.user_login.UserLoginResponse
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.net.RestObservable
import com.tamam.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.btn_signin
import kotlinx.android.synthetic.main.activity_forgot_password.emailEdittext
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener,
    Observer<RestObservable> {
    val mContext: Context = this
    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_forgot_password)
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        iv_back.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            } R.id.btn_signin->{

            if (emailEdittext.getText().toString().isEmpty()) {
                emailEdittext.requestFocus()
                emailEdittext.setError(resources.getString(R.string.please_enter_email))
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.getText().toString()).matches()) {
                emailEdittext.requestFocus()
                emailEdittext.setError(resources.getString(R.string.please_enter_valid_email))
            }
            else{
                val hashMap = HashMap<String,String>()
                hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
                viewModel.postforgotpasswordApi(this, true, hashMap)
                viewModel.homeResponse.observe(this, this)

            }


            }
        }
    }

    override fun onChanged(t: RestObservable?) {
        when(t?.status){
            Status.SUCCESS ->{
                if (t.data is ForgotPasswordResponse){
                    val response = t.data.body
                    startActivity(Intent(mContext, LoginActivity::class.java))
                }

            }
            Status.ERROR->{
                Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG).show()

            }
        }
    }
}