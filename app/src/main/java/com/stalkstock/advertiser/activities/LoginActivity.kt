package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.api.Status
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.MainCommercialActivity
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.SelectuserActivity
import com.stalkstock.consumer.activities.SignupConsumerActivity
import com.stalkstock.driver.HomeActivity
import com.stalkstock.consumer.model.UserLoginResponse
import com.stalkstock.utils.others.getPrefrence
import com.stalkstock.vender.ui.BottomnavigationScreen
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.driver.SignupActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {

    val viewModel: HomeViewModel by viewModels()

    val mContext: Context = this

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
//            goingToHome()


        }
        iv_gmail.setOnClickListener {
//            goingToHome()


        }

        iv_twitter.setOnClickListener {
//            goingToHome()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tv_forgot_password -> {
                startActivity(Intent(mContext, ForgotPasswordActivity::class.java))
            }
            R.id.back -> {
                // super.onBackPressed();
                startActivity(Intent(this@LoginActivity, SelectuserActivity::class.java))
                finishAffinity()
            }

            R.id.tv_signup -> {
                goingToSignUp()

            }


            R.id.signup -> {
                goingToSignUp()

            }


            R.id.btn_signin -> {
                val userType = MyApplication.instance.getString("usertype")
                if(userType.equals("4"))
                {
                    startActivity(Intent(mContext, MainCommercialActivity::class.java))
                    finishAffinity()
                }else if(userType.equals("2"))
                {
                    startActivity(Intent(mContext, HomeActivity::class.java))
                    finishAffinity()
                }
                else {
                    emailEdittext.text.toString()
                    passwordEdittext.text.toString()
                    SetValidation()
                }

            }


        }
    }


    override fun onBackPressed() {
        //  super.onBackPressed()
        // super.onBackPressed();
        startActivity(Intent(this@LoginActivity, SelectuserActivity::class.java))
        finishAffinity()
    }

    fun goingToHome() {
        if (MyApplication.instance.getString("usertype").equals("5")) {
            startActivity(Intent(mContext, MainActivity::class.java))
            finishAffinity()
        } else if (MyApplication.instance.getString("usertype").equals("1")) {
            startActivity(Intent(mContext, MainConsumerActivity::class.java))
            finishAffinity()
        } else if (MyApplication.instance.getString("usertype").equals("4")) {
            startActivity(Intent(mContext, MainCommercialActivity::class.java))
            finishAffinity()
        } else if (MyApplication.instance.getString("usertype").equals("3")) {
            startActivity(Intent(mContext, BottomnavigationScreen::class.java))
            finishAffinity()
        } else if (MyApplication.instance.getString("usertype").equals("2")) {
            startActivity(Intent(mContext, HomeActivity::class.java))
            finishAffinity()
        }
    }


    fun goingToSignUp() {
        /* OLD
* 1-adv
* 2-commercial
* 3-consumer
* 4-vendor
* 5-driver
* */
        /*
    1=>user
    2=>driver
    3=>vendor
    4=>commercial
    5=>advertiser* */

        if (MyApplication.instance.getString("usertype").equals("4")) {
            startActivity(Intent(mContext, SignupAdvertiserNCommercialNVendor::class.java))
        } else if (MyApplication.instance.getString("usertype").equals("1")) {
            startActivity(Intent(mContext, SignupConsumerActivity::class.java))
        } else if (MyApplication.instance.getString("usertype").equals("5")) {
            startActivity(Intent(mContext, SignupAdvertiserNCommercialNVendor::class.java))
        } else if (MyApplication.instance.getString("usertype").equals("3")) {
//           startActivity(Intent(mContext, SignupActivity::class.java))
//            startActivity(Intent(mContext, SignUpVendor::class.java))
            startActivity(Intent(mContext, SignupAdvertiserNCommercialNVendor::class.java))
        } else if (MyApplication.instance.getString("usertype").equals("2")) {
            startActivity(Intent(mContext, SignupActivity::class.java))
        }
    }


    fun SetValidation() {
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
        } else {
            /***
             * Data willl set here to sent in api call
             */
            val hashMap = HashMap<String, String>()
            hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.device_type] = GlobalVariables.PARAM.android_device_type
            hashMap[GlobalVariables.PARAM.device_token] =
                getPrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, "666666")
            //Api will call here

            viewModel.postuserloginApi(this, true, hashMap)
            viewModel.homeResponse.observe(this, this)

//            if(!getPrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, "").isNullOrEmpty()){
//                viewModel.postuserloginApi(this, true,hashMap)
//                viewModel.homeResponse.observe(this, this)
//            }else{
//                Toast.makeText(this, "Device token is required ", Toast.LENGTH_SHORT).show()
//            }
        }

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserLoginResponse) {
                    val mResponse: UserLoginResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if (mResponse.body.role == 1)
                            setData(mResponse)
                        else if (mResponse.body.role == 3)
                            setDataVendor(mResponse)
                        goingToHome()
                    } else {
//                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setDataVendor(data: UserLoginResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.token)
        MyApplication.instance.setString("usertype",data.body.role.toString())
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

    private fun setData(mResponse: UserLoginResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, "1")
        MyApplication.instance.setString("usertype","1")
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

}