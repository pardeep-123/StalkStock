package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.pojo.SendOtpResponse
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.driver.UploadDocActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.Util
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.verification.*
import okhttp3.RequestBody

class Verification : AppCompatActivity(), Observer<RestObservable> {

    lateinit var mUtils: Util
    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var otp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.verification)
        tvPhoneNo.text = intent.getStringExtra("phoneNo")!!

        mUtils = Util()

        sendOtpApiHit()

        verify_backarrow.setOnClickListener { onBackPressed() }

        resendotp.setOnClickListener {
            sendOtpApiHit()
        }

        verifybutton.setOnClickListener {
            if (validateData()) {
                if (MyApplication.instance.getString("usertype").equals("1")) {
                    startActivity(Intent(this, MainConsumerActivity::class.java))
                    finishAffinity()
                } else if (MyApplication.instance.getString("usertype").equals("2")) {
                    goToIntent()
                }
            }
        }

    }


    private fun validateData(): Boolean {
        if (otpPin.text.toString().trim().isEmpty()) {
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_otp))
            return false
        } else if (otp != otpPin.text.toString()) {
            AppUtils.showErrorAlert(this, resources.getString(R.string.otp_does_not_match))
            return false
        } else return true
    }

    private fun sendOtpApiHit() {
        val hashMap = HashMap<String, RequestBody>()
        hashMap["mobile"] = mUtils.createPartFromString(intent.getStringExtra("phoneNo")!!)
        viewModel.sendOtp(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)
    }


    private fun goToIntent() {
        val intentUploadDoc = Intent(this, UploadDocActivity::class.java)
        intentUploadDoc.putExtra("fName", intent.getStringExtra("fName"))
        intentUploadDoc.putExtra("lName", intent.getStringExtra("lName"))
        intentUploadDoc.putExtra("eId", intent.getStringExtra("eId"))
        intentUploadDoc.putExtra("mNumber", intent.getStringExtra("mNumber"))
        intentUploadDoc.putExtra("vType", intent.getStringExtra("vType"))
        intentUploadDoc.putExtra("make", intent.getStringExtra("make"))
        intentUploadDoc.putExtra("model", intent.getStringExtra("model"))
        intentUploadDoc.putExtra("city", intent.getStringExtra("city"))
        intentUploadDoc.putExtra("state", intent.getStringExtra("state"))
        intentUploadDoc.putExtra("country", intent.getStringExtra("country"))
        intentUploadDoc.putExtra("pass", intent.getStringExtra("pass"))
        intentUploadDoc.putExtra("profileImage", intent.getStringExtra("profileImage"))
        intentUploadDoc.putExtra("addressLine2", intent.getStringExtra("addressLine2"))
        startActivity(intentUploadDoc)
        finish()
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is SendOtpResponse) {
                    val data = it.data as SendOtpResponse
                    if (data.code == 200) {
                        Log.i("====", data.message)
                        otp = data.body.otp.toString()
                        otpPin.text?.clear()
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }


}
