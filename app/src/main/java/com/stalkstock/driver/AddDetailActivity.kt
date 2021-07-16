package com.stalkstock.driver

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_add_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.verification_popup.*
import okhttp3.RequestBody

class AddDetailActivity : BaseActivity(), Observer<RestObservable> {

    val viewModel: DriverViewModel by viewModels()
    var mProfileImage = ""
    var mLicenseimage1 = ""
    var mLicenseimage2 = ""
    var mRegistrationImage = ""
    var mInsuranceImage = ""
    var mHashMap : HashMap<String, String> = HashMap()
    override fun getContentId(): Int {
        return R.layout.activity_add_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        tv_heading.text = "Add Details"
        iv_back.setOnClickListener {finish()}
        btn_Continue.setOnClickListener {
            signUpApi()
//            dialogconfirmation()
        }

        if (intent.hasExtra("driverData")) {
            mHashMap = intent.getSerializableExtra("driverData") as HashMap<String, String>
            mProfileImage = intent.getStringExtra("profileImage")!!
            mLicenseimage1 = intent.getStringExtra("license1")!!
            mLicenseimage2 = intent.getStringExtra("license2")!!
            mRegistrationImage = intent.getStringExtra("registration")!!
            mInsuranceImage = intent.getStringExtra("insurance")!!
        }
    }


    private fun dialogconfirmation() {
        val  dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.verification_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_ok11.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            dialog.dismiss()
            finishAffinity()

        }



        dialog.show()
    }

    private fun signUpApi()
    {
        if (checkStringNull(edText.text.toString()))
        {
            edText.requestFocus()
            edText.setError(resources.getString(R.string.please_enter_driver_license_number))
        }else if (checkStringNull(edText1.text.toString()))
        {
            edText1.requestFocus()
            edText1.setError(resources.getString(R.string.please_enter_driver_license_exp_date))
        }else if (checkStringNull(edText2.text.toString()))
        {
            edText2.requestFocus()
            edText2.setError(resources.getString(R.string.please_enter_registration_number))
        }else if (checkStringNull(edText3.text.toString()))
        {
            edText3.requestFocus()
            edText3.setError(resources.getString(R.string.please_enter_registration_expdate))
        }else{
        val hashMap = HashMap<String, RequestBody>()
        hashMap[GlobalVariables.PARAM.firstname] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.firstname))
        hashMap[GlobalVariables.PARAM.lastname] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.lastname))
        hashMap[GlobalVariables.PARAM.email] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.email))
        hashMap[GlobalVariables.PARAM.mobile] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.mobile))
        hashMap[GlobalVariables.PARAM.vehicleType] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.vehicleType))
        hashMap[GlobalVariables.PARAM.vehicleMake] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.vehicleMake))
        hashMap[GlobalVariables.PARAM.vehicleModel] =
            mUtils.createPartFromString(mHashMap.get(GlobalVariables.PARAM.vehicleModel))
        hashMap[GlobalVariables.PARAM.city] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.city))
        hashMap[GlobalVariables.PARAM.state] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.state))
        hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(mHashMap.get(
            GlobalVariables.PARAM.country))
        hashMap[GlobalVariables.PARAM.password] = mUtils.createPartFromString(mHashMap.get(GlobalVariables.PARAM.password))
        hashMap[GlobalVariables.PARAM.licenceNumber] = mUtils.createPartFromString(edText.text.toString())
        hashMap[GlobalVariables.PARAM.registrationNumber] = mUtils.createPartFromString(edText2.text.toString())
        hashMap[GlobalVariables.PARAM.licenceExpiryDate] = mUtils.createPartFromString(edText1.text.toString())
        hashMap[GlobalVariables.PARAM.registrationExpiryDate] = mUtils.createPartFromString(edText3.text.toString())
        viewModel.driverSignUpApi(this, true, hashMap,mProfileImage,mLicenseimage1,mLicenseimage2,mRegistrationImage,mInsuranceImage,mUtils)
        viewModel.mResponse.observe(this, this)
    }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                /*if (it.data is VendorSignupResponse) {
                    val data = it.data as VendorSignupResponse
                    if (MyApplication.instance.getString("usertype").equals("3")) {
                        setData(data)
                        startActivity(Intent(this, BottomnavigationScreen::class.java))
                        //startActivity(Intent(mContext, Verification::class.java))
//                finish()
                    } else {
                        startActivity(Intent(mContext, LoginActivity::class.java))
                        finish()
                    }
                }*/
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
