package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.advertiser.activities.pojo.SendOtpResponse
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.CommercialSignUpResponse
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_verification.*
import okhttp3.RequestBody

class Verification : BaseActivity(), View.OnClickListener, Observer<RestObservable> {

    var verfiy: Button? = null
    var verfy_backarrow: ImageView? = null
    var latitude = ""
    var longitude = ""
    var firstName = ""
    var lastName = ""
    var shopName = ""
    var shopDescription = ""
    var buisnessTypeId = ""
    var deliveryType = ""
    var buisnessLicense = ""
    var email = ""
    var mobile = ""
    var buisnessPhone = ""
    var website = ""
    var shopAddress = ""
    var city = ""
    var state = ""
    var postalCode = ""
    var country = ""
    var password = ""
    var firstimage = ""
    var otp = ""

    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun getContentId(): Int {
        return R.layout.activity_verification
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verfy_backarrow = findViewById(R.id.verify_backarrow)

        otpPin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {

                if (s.toString().length == 4) {
                    //hide keyboard
                    CommonMethods.hideKeyboard(this@Verification, otpPin)
                }
            }
        })

        otp = intent.getStringExtra("otp")!!
        if (MyApplication.instance.getString("usertype").equals("3")) {
            latitude = intent.getStringExtra("latitude")!!
            longitude = intent.getStringExtra("longitude")!!
            firstName = intent.getStringExtra("firstName")!!
            lastName = intent.getStringExtra("lastname")!!
            shopName = intent.getStringExtra("shopName")!!
            shopDescription = intent.getStringExtra("shopDescription")!!
            buisnessTypeId = intent.getStringExtra("buisnessTypeId")!!
            deliveryType = intent.getStringExtra("deliveryType")!!
            buisnessLicense = intent.getStringExtra("buisnessLicense")!!
            email = intent.getStringExtra("email")!!
            mobile = intent.getStringExtra("mobile")!!
            tvPhoneNumber.text = mobile
            buisnessPhone = intent.getStringExtra("buisnessPhone").toString()
            website = intent.getStringExtra("website").toString()
            shopAddress = intent.getStringExtra("shopAddress").toString()
            city = intent.getStringExtra("city").toString()
            state = intent.getStringExtra("state").toString()
            postalCode = intent.getStringExtra("postalCode").toString()
            country = intent.getStringExtra("country").toString()
            password = intent.getStringExtra("password").toString()
            firstimage = intent.getStringExtra("firstimage").toString()
        }else{
            mobile = intent.getStringExtra("mobile")!!
            tvPhoneNumber.text = mobile
        }


        verfiy = findViewById(R.id.verifybutton)
        verfiy?.setOnClickListener(this)
        verfy_backarrow?.setOnClickListener(this)
        resendotp?.setOnClickListener(this)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.verify_backarrow -> onBackPressed()
            R.id.resendotp -> {
                val hashMap = HashMap<String, RequestBody>()
                hashMap["mobile"] = mUtils.createPartFromString(mobile)
                viewModel.sendOtp(this, true, hashMap)
                viewModel.homeResponse.observe(this, this)
            }

            R.id.verifybutton -> {
                if (otp != otpPin.text.toString()) {
                    AppUtils.showErrorAlert(this, resources.getString(R.string.otp_does_not_match))
                } else {
                    if (MyApplication.instance.getString("usertype").equals("3")) {
                        val hashMap = HashMap<String, RequestBody>()
                        hashMap[GlobalVariables.PARAM.firstname] =
                            mUtils.createPartFromString(firstName)
                        hashMap["latitude"] = mUtils.createPartFromString(latitude)
                        hashMap["longitude"] = mUtils.createPartFromString(longitude)
                        hashMap[GlobalVariables.PARAM.lastname] =
                            mUtils.createPartFromString(lastName)
                        hashMap[GlobalVariables.PARAM.shopName] =
                            mUtils.createPartFromString(shopName)
                        hashMap[GlobalVariables.PARAM.shopDescription] =
                            mUtils.createPartFromString(shopDescription)
                        hashMap[GlobalVariables.PARAM.buisnessTypeId] =
                            mUtils.createPartFromString(buisnessTypeId)
                        hashMap[GlobalVariables.PARAM.deliveryType] =
                            mUtils.createPartFromString(deliveryType)
                        hashMap[GlobalVariables.PARAM.buisnessLicense] =
                            mUtils.createPartFromString(buisnessLicense)
                        hashMap[GlobalVariables.PARAM.email] =
                            mUtils.createPartFromString(email)
                        hashMap[GlobalVariables.PARAM.mobile] =
                            mUtils.createPartFromString(mobile)
                        hashMap[GlobalVariables.PARAM.businessPhone] =
                            mUtils.createPartFromString(buisnessPhone)
                        hashMap[GlobalVariables.PARAM.website] =
                            mUtils.createPartFromString(website)
                        hashMap[GlobalVariables.PARAM.shopAddress] =
                            mUtils.createPartFromString(shopAddress)
                        hashMap[GlobalVariables.PARAM.city] =
                            mUtils.createPartFromString(city)
                        hashMap[GlobalVariables.PARAM.state] =
                            mUtils.createPartFromString(state)
                        hashMap[GlobalVariables.PARAM.postalCode] =
                            mUtils.createPartFromString(postalCode)
                        hashMap[GlobalVariables.PARAM.country] =
                            mUtils.createPartFromString(country)
                        hashMap[GlobalVariables.PARAM.password] =
                            mUtils.createPartFromString(password)

                        viewModel.postvendorsignupApi(this, true, hashMap, firstimage, mUtils)
                        viewModel.homeResponse.observe(this, this)
                    } else if (MyApplication.instance.getString("usertype").equals("4")) {
                        val hashMap = HashMap<String, RequestBody>()
                        hashMap["latitude"] =
                            mUtils.createPartFromString(intent.getStringExtra("latitude"))
                        hashMap["longitude"] =
                            mUtils.createPartFromString(intent.getStringExtra("longitude"))
                        hashMap[GlobalVariables.PARAM.firstname] =
                            mUtils.createPartFromString(intent.getStringExtra("firstName"))
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
                        hashMap[GlobalVariables.PARAM.deliveryType] =
                            mUtils.createPartFromString(intent.getStringExtra("deliveryType"))

                        hashMap[GlobalVariables.PARAM.email] =
                            mUtils.createPartFromString(intent.getStringExtra("email"))
                        hashMap[GlobalVariables.PARAM.mobile] =
                            mUtils.createPartFromString(intent.getStringExtra("mobile"))
                        hashMap[GlobalVariables.PARAM.businessPhone] =
                            mUtils.createPartFromString(intent.getStringExtra("businessPhone"))
                        hashMap[GlobalVariables.PARAM.website] =
                            mUtils.createPartFromString(intent.getStringExtra("website"))
                        hashMap[GlobalVariables.PARAM.buisnessAddress] =
                            mUtils.createPartFromString(intent.getStringExtra("buisnessAddress"))
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


                        viewModel.commrercialSignupApi(this, true, hashMap, firstimage, mUtils)
                        viewModel.homeResponse.observe(this, this)
                    }

                }
            }

        }
    }

    private fun verifyloDailogMethod() {
        val logoutUpdatedDialog = Dialog(this, R.style.Theme_Dialog)
        logoutUpdatedDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialog.setContentView(R.layout.verficationalertdialog)
        logoutUpdatedDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialog.setCancelable(true)
        logoutUpdatedDialog.setCanceledOnTouchOutside(false)
        logoutUpdatedDialog.window!!.setGravity(Gravity.CENTER)
        logoutUpdatedDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btncontinue = logoutUpdatedDialog.findViewById<Button>(R.id.verify_continuebutton)
        btncontinue.setOnClickListener { //                                        Intent intent = new Intent(ChatBox.this, MessageFragment.class);
//                                        startActivity(intent);
            startActivity(Intent(this@Verification, AddBusinessDetails::class.java))
            logoutUpdatedDialog.dismiss()
        }
        logoutUpdatedDialog.show()
    }

    override fun onChanged(it: RestObservable) {
        when {
            it.status == Status.SUCCESS -> {
                if (it.data is VendorSignupResponse) {
                    val data = it.data as VendorSignupResponse
                    setData(data)
                    AppUtils.showSuccessAlert(
                        this,
                        "Sign up successfully !! please login to continue"
                    )
                    Handler(Looper.getMainLooper()).postDelayed({
                        startActivity(Intent(this, LoginActivity::class.java))
                        //Do something after 100ms
                    }, 2000)
                }
                if (it.data is SendOtpResponse) {
                    val data = it.data as SendOtpResponse
                    if (data.code == 200) {
                        Log.i("====", data.message)
                        otp = data.body.otp.toString()
                        otpPin.text?.clear()
                    }
                }
                if (it.data is CommercialSignUpResponse) {
                    val data = it.data as CommercialSignUpResponse
                    if (data.code == 200) {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                        // setCommercialData(data)
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
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

    private fun setData(data: VendorSignupResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.token)
        savePrefrence(
            GlobalVariables.SHARED_PREF.USER_TYPE,
            MyApplication.instance.getString("usertype").toString()
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.AUTH_KEY, data.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.token, data.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.id, data.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.role, data.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.verified, data.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.status, data.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.email, data.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.mobile, data.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.deviceType, data.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.notification, data.body.notification)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.remember_token,
            data.body.remember_token
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.created, data.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.updated, data.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.createdAt, data.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.updatedAt, data.body.updatedAt)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.vendorID, data.body.vendorDetail.id)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.approvalStatus,
            data.body.vendorDetail.approvalStatus
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.approvalStatusReason,
            data.body.vendorDetail.approvalStatusReason
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.firstName,
            data.body.vendorDetail.firstName
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.lastName, data.body.vendorDetail.lastName)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.image, data.body.vendorDetail.image)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.buisnessPhone,
            data.body.vendorDetail.buisnessPhone
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.shopLogo, data.body.vendorDetail.shopLogo)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.buisnessTypeId,
            data.body.vendorDetail.buisnessTypeId
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.shopName, data.body.vendorDetail.shopName)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.buisnessLicense,
            data.body.vendorDetail.buisnessLicense
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.website, data.body.vendorDetail.website)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.city, data.body.vendorDetail.city)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.state, data.body.vendorDetail.state)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.country, data.body.vendorDetail.country)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.postalCode,
            data.body.vendorDetail.postalCode
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.latitude, data.body.vendorDetail.latitude)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.longitude,
            data.body.vendorDetail.longitude
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.geoLocation,
            data.body.vendorDetail.geoLocation
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.shopAddress,
            data.body.vendorDetail.shopAddress
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.addressLine2,
            data.body.vendorDetail.addressLine2
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.shopDescription,
            data.body.vendorDetail.shopDescription
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.shopCharges,
            data.body.vendorDetail.shopCharges
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.deliveryTime,
            data.body.vendorDetail.deliveryTime
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.paymentPolicy,
            data.body.vendorDetail.paymentPolicy
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.deliveryPolicy,
            data.body.vendorDetail.deliveryPolicy
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.sellerInformation,
            data.body.vendorDetail.sellerInformation
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.taxInPercent,
            data.body.vendorDetail.taxInPercent
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.taxValue, data.body.vendorDetail.taxValue)
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.bankName, data.body.vendorDetail.bankName)
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.bankBranch,
            data.body.vendorDetail.bankBranch
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.accountHolderName,
            data.body.vendorDetail.accountHolderName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.accountNumber,
            data.body.vendorDetail.accountNumber
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.bsbNumber,
            data.body.vendorDetail.bsbNumber
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.ifscSwiftCode,
            data.body.vendorDetail.ifscSwiftCode
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_VENDOR.bankAddress,
            data.body.vendorDetail.bankAddress
        )
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.userId, data.body.vendorDetail.userId)
    }
}