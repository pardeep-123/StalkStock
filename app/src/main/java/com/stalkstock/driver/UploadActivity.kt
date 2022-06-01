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
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_upload.*
import kotlinx.android.synthetic.main.fragment_h_ome.*
import kotlinx.android.synthetic.main.home_popup.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.iv_back
import okhttp3.RequestBody

class UploadActivity : BaseActivity() {

    var mProfileImage = ""
    var mLicenseimage1 = ""
    var mLicenseimage2 = ""
    var mRegistrationImage = ""
    var mInsuranceImage = ""
    var fName: String = ""
    var lName : String= ""
    var eId : String= ""
    var mNumber: String = ""
    var vType: String = ""
    var model : String= ""
    var make: String = ""
    var city: String = ""
    var state: String = ""
    var pass : String= ""
    var country: String = ""
    var addressLine2: String = ""
    var mHashMap : HashMap<String, String> = HashMap()


    override fun getContentId(): Int {
        return R.layout.activity_upload
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            window.statusBarColor = Color.WHITE;
        }

        tv_heading.text = getString(R.string.upload_documents)
        iv_back.setOnClickListener {finish()}
        btn_continue.setOnClickListener {
            startActivity(Intent(this, AddDetailActivity::class.java)
                .putExtra("fName", fName)
                .putExtra("lName", lName)
                .putExtra("eId", eId)
                .putExtra("mNumber", mNumber)
                .putExtra("vType", vType)
                .putExtra("make", make)
                .putExtra("model", model)
                .putExtra("city", city)
                .putExtra("state", state)
                .putExtra("country", country)
                .putExtra("pass", pass)
                .putExtra("profileImage",mProfileImage)
                .putExtra("license1",mLicenseimage1)
                .putExtra("license2",mLicenseimage2)
                .putExtra("registration",mRegistrationImage)
                .putExtra("insurance",mInsuranceImage)
                .putExtra("addressLine2",addressLine2)
            )
        }

            fName = intent.getStringExtra("fName").toString()
            lName = intent.getStringExtra("lName").toString()
            eId = intent.getStringExtra("eId").toString()
            mNumber = intent.getStringExtra("mNumber").toString()
            vType = intent.getStringExtra("vType").toString()
            make = intent.getStringExtra("make").toString()
            model = intent.getStringExtra("model").toString()
            city = intent.getStringExtra("city").toString()
            state = intent.getStringExtra("state").toString()
            country = intent.getStringExtra("country").toString()
            pass = intent.getStringExtra("pass").toString()
        addressLine2 = intent.getStringExtra("addressLine2").toString()


            mProfileImage = intent.getStringExtra("profileImage")!!
            mLicenseimage1 = intent.getStringExtra("license1")!!
            mLicenseimage2 = intent.getStringExtra("license2")!!
            mRegistrationImage = intent.getStringExtra("registration")!!
            mInsuranceImage = intent.getStringExtra("insurance")!!
            Glide.with(this).load(mLicenseimage1).into(ivImageLic1)
            Glide.with(this).load(mLicenseimage2).into(ivImageLic2)
            Glide.with(this).load(mRegistrationImage).into(ivRegistration)
            Glide.with(this).load(mInsuranceImage).into(ivInsurance)

    }



    private fun dialogconfirmation() {
        val  dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.home_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_accept.setOnClickListener {
            dialog.dismiss()

        }
        dialog.btn_decline.setOnClickListener{
            dialog.dismiss()
            rl_top.visibility = View.VISIBLE
            rl_tv.visibility = View.GONE
        }


        dialog.show()
    }


}
