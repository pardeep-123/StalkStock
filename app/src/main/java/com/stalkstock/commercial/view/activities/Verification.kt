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
import com.stalkstock.advertiser.activities.MainActivity
import com.stalkstock.advertiser.activities.pojo.SendOtpResponse
import com.stalkstock.advertiser.model.AdvertiserSignUpResponse
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.model.ModelSignupUser
import com.stalkstock.driver.UploadDocActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.verification.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.HashMap

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
                    signupUserAPI()
                } else if (MyApplication.instance.getString("usertype").equals("2")) {
                    goToIntent()
                } else if (MyApplication.instance.getString("usertype").equals("5")) {
                    signUpAdvertiserApiHit()
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


    private fun signupUserAPI() {
        val map = java.util.HashMap<String, RequestBody>()
        map["first_name"] = mUtils.createPartFromString(intent.getStringExtra("first_name"))
        map["last_name"] = mUtils.createPartFromString(intent.getStringExtra("last_name"))
        map["email"] = mUtils.createPartFromString(intent.getStringExtra("email"))
        map["mobile"] = mUtils.createPartFromString(intent.getStringExtra("phoneNo"))
        map["password"] = mUtils.createPartFromString(intent.getStringExtra("password"))
        map["currencyType"] =
            mUtils.createPartFromString(Currency.getInstance(Locale.getDefault()).toString())
        viewModel.getusersignupApi(this, true, map, intent.getStringExtra("firstimage")!!, mUtils)
        viewModel.homeResponse.observe(this, this)

    }


    private fun signUpAdvertiserApiHit() {
        val hashMap = HashMap<String, RequestBody>()
        hashMap[GlobalVariables.PARAM.firstname] =
            mUtils.createPartFromString(intent.getStringExtra("firstname"))
        hashMap[GlobalVariables.PARAM.lastname] =
            mUtils.createPartFromString(intent.getStringExtra("lastname"))
        hashMap[GlobalVariables.PARAM.buisnessName] =
            mUtils.createPartFromString(intent.getStringExtra("buisnessName"))
        hashMap[GlobalVariables.PARAM.buisnessDescription] =
            mUtils.createPartFromString(intent.getStringExtra("buisnessDescription"))
        hashMap[GlobalVariables.PARAM.buisnessTypeId] =
            mUtils.createPartFromString(intent.getStringExtra("buisnessTypeId"))
        hashMap[GlobalVariables.PARAM.buisnessLicense] =
            mUtils.createPartFromString(intent.getStringExtra("buisnessLicense"))
        hashMap[GlobalVariables.PARAM.email] =
            mUtils.createPartFromString(intent.getStringExtra("email"))
        hashMap[GlobalVariables.PARAM.mobile] =
            mUtils.createPartFromString(intent.getStringExtra("phoneNo"))
        hashMap[GlobalVariables.PARAM.businessPhone] =
            mUtils.createPartFromString(intent.getStringExtra("businessPhone"))
        hashMap[GlobalVariables.PARAM.website] =
            mUtils.createPartFromString(intent.getStringExtra("website"))
        hashMap[GlobalVariables.PARAM.buisnessAddress] =
            mUtils.createPartFromString(intent.getStringExtra("buisnessAddress"))
        hashMap["addressLine2"] = mUtils.createPartFromString(intent.getStringExtra("addressLine2"))
        hashMap[GlobalVariables.PARAM.city] =
            mUtils.createPartFromString(intent.getStringExtra("city"))
        hashMap[GlobalVariables.PARAM.state] =
            mUtils.createPartFromString(intent.getStringExtra("state"))
        hashMap[GlobalVariables.PARAM.postalCode] =
            mUtils.createPartFromString(intent.getStringExtra("postalCode"))
        hashMap[GlobalVariables.PARAM.country] =
            mUtils.createPartFromString(intent.getStringExtra("country"))
        hashMap[GlobalVariables.PARAM.password] =
            mUtils.createPartFromString(intent.getStringExtra("password"))
        hashMap["currencyType"] =
            mUtils.createPartFromString(Currency.getInstance(Locale.getDefault()).toString())


        viewModel.postAdvertiserSignUpApi(
            this,
            true,
            hashMap,
            intent.getStringExtra("firstimage")!!,
            mUtils
        )
        viewModel.homeResponse.observe(this, this)
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
                } else if (it.data is ModelSignupUser) {
                    val mResponse: ModelSignupUser = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setUserData(mResponse)
                        startActivity(Intent(this, MainConsumerActivity::class.java))
                        finishAffinity()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                } else if (it.data is AdvertiserSignUpResponse) {
                    val data = it.data
                    if (data.code == 200) {
                        setAdvertiserData(data)
                        startActivity(Intent(this, MainActivity::class.java))
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

    private fun setUserData(mResponse: ModelSignupUser) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "1")
        savePrefrence(GlobalVariables.SHARED_PREF_USER.AUTH_KEY, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.token, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.verified, mResponse.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.status, mResponse.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.notification, mResponse.body.notification)
        savePrefrence(
            GlobalVariables.SHARED_PREF_USER.remember_token,
            mResponse.body.remember_token
        )
        savePrefrence(GlobalVariables.SHARED_PREF_USER.created, mResponse.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.updated, mResponse.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.createdAt, mResponse.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.updatedAt, mResponse.body.updatedAt)
    }

    private fun setAdvertiserData(data: AdvertiserSignUpResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.authKey)
        savePrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.AUTH_KEY, data.body.authKey)
        savePrefrence(
            GlobalVariables.SHARED_PREF.USER_TYPE,
            MyApplication.instance.getString("usertype").toString()
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.token, data.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.id, data.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.role, data.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.verified, data.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.status, data.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.email, data.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.mobile, data.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceType, data.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.notification, data.body.notification)
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.remember_token,
            data.body.remember_token
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.created, data.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.updated, data.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.createdAt, data.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.updatedAt, data.body.updatedAt)
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.advertiserId,
            data.body.advertiserDetail.id
        )

        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.firstName,
            data.body.advertiserDetail.firstName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.lastName,
            data.body.advertiserDetail.lastName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.image,
            data.body.advertiserDetail.image
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessPhone,
            data.body.advertiserDetail.buisnessPhone
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessLogo,
            data.body.advertiserDetail.buisnessLogo
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessTypeId,
            data.body.advertiserDetail.buisnessTypeId
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessName,
            data.body.advertiserDetail.buisnessName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessLicense,
            data.body.advertiserDetail.buisnessLicense
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.website,
            data.body.advertiserDetail.website
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.city, data.body.advertiserDetail.city)
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.state,
            data.body.advertiserDetail.state
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.country,
            data.body.advertiserDetail.country
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.postalCode,
            data.body.advertiserDetail.postalCode
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessAddress,
            data.body.advertiserDetail.buisnessAddress
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.addressLine2,
            data.body.advertiserDetail.addressLine2
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessDescription,
            data.body.advertiserDetail.buisnessDescription
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.userId,
            data.body.advertiserDetail.userId
        )
    }

}
