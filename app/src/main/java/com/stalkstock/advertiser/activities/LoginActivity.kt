package com.stalkstock.advertiser.activities
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
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
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.FacebookHelper
import com.stalkstock.utils.FacebookHelper.*
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener, Observer<RestObservable>,
    FacebookHelperCallback {

    val viewModel: HomeViewModel by viewModels()

    val mContext: Context = this
    var facebookHelper: FacebookHelper? = null
    var isFb = ""
    private var googleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001
    var social_image=""
    var social_id=""
    var social_email=""
    var social_name=""

    override fun getContentId(): Int { return R.layout.activity_login }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_forgot_password.setOnClickListener(this)
        tv_signup.setOnClickListener(this)
        btn_signin.setOnClickListener(this)
        signup.setOnClickListener(this)
        back.setOnClickListener(this)

        iv_fb.setOnClickListener {
            isFb = "fb"
            facebookHelper!!.login(this)
            iv_fb.isEnabled = false
        }
        iv_gmail.setOnClickListener {
            iv_gmail.isEnabled = false
            val signInIntent = googleSignInClient!!.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        facebookHelper = FacebookHelper(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        iv_gmail.isEnabled = true
        iv_fb.isEnabled = true

        if (requestCode == RC_SIGN_IN ) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
            catch (e: Exception) {e.printStackTrace() }
        }
        else {
            if (isFb == "fb"){
                facebookHelper!!.onResult(requestCode, resultCode, data)
            }
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            callGoogleApi(account)
        } catch (e: ApiException) {
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
            e.printStackTrace()
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
                emailEdittext.text.toString()
                passwordEdittext.text.toString()
                setValidation()
//                if(userType.equals("4"))
//                {
//                    startActivity(Intent(mContext, MainCommercialActivity::class.java))
//                    finishAffinity()
//                }/*else if(userType.equals("2"))
//                {
//                    startActivity(Intent(mContext, HomeActivity::class.java))
//                    finishAffinity()
//                }*/
//                else {
//                    emailEdittext.text.toString()
//                    passwordEdittext.text.toString()
//                    setValidation()
//                }
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@LoginActivity, SelectuserActivity::class.java))
        finishAffinity()
    }

    private fun goingToHome() {
        when {
            MyApplication.instance.getString("usertype").equals("5") -> {
                startActivity(Intent(mContext, MainActivity::class.java))
                finishAffinity()
            }
            MyApplication.instance.getString("usertype").equals("1") -> {
                startActivity(Intent(mContext, MainConsumerActivity::class.java))
                finishAffinity()
            }
            MyApplication.instance.getString("usertype").equals("4") -> {
                startActivity(Intent(mContext, MainCommercialActivity::class.java))
                finishAffinity()
            }
            MyApplication.instance.getString("usertype").equals("3") -> {
                startActivity(Intent(mContext, BottomnavigationScreen::class.java))
                finishAffinity()
            }
            MyApplication.instance.getString("usertype").equals("2") -> {
                startActivity(Intent(mContext, HomeActivity::class.java))
                finishAffinity()
            }
        }
    }

    private fun goingToSignUp() {

        when {
            MyApplication.instance.getString("usertype").equals("4") -> {
                startActivity(Intent(mContext, SignupAdvertiserNCommercialNVendor::class.java))
            }
            MyApplication.instance.getString("usertype").equals("1") -> {
                startActivity(Intent(mContext, SignupConsumerActivity::class.java))
            }
            MyApplication.instance.getString("usertype").equals("5") -> {

                startActivity(Intent(mContext, SignUpActivity::class.java))
            }
            MyApplication.instance.getString("usertype").equals("3") -> {
                startActivity(Intent(mContext, SignupAdvertiserNCommercialNVendor::class.java))
            }
            MyApplication.instance.getString("usertype").equals("2") -> {
                startActivity(Intent(mContext, SignupActivity::class.java))
            }
        }
    }

    private fun setValidation() {
        if (emailEdittext.text.toString().isEmpty()) {
            emailEdittext.requestFocus()
            emailEdittext.error = resources.getString(R.string.please_enter_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.text.toString()).matches()) {
            emailEdittext.requestFocus()
            emailEdittext.error = resources.getString(R.string.please_enter_valid_email)
        } else if (passwordEdittext.text.toString().isEmpty()) {
            passwordEdittext.requestFocus()
            passwordEdittext.error = resources.getString(R.string.please_enter_password)
        } else {
            /***
             * Data willl set here to sent in api call
             */
            val userType = MyApplication.instance.getString("usertype")
            if (userType.equals("5")) {
                val hashMap = HashMap<String, String>()
                hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
                hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()
                hashMap[GlobalVariables.PARAM.device_type] =
                    GlobalVariables.PARAM.android_device_type
                hashMap[GlobalVariables.PARAM.device_token] = getPrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, "666666")
                viewModel.postuserloginApi(this, true, hashMap)
                viewModel.homeResponse.observe(this, this)
            } else {
                val hashMap = HashMap<String, String>()
                hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
                hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()
                hashMap[GlobalVariables.PARAM.device_type] =
                    GlobalVariables.PARAM.android_device_type
                hashMap[GlobalVariables.PARAM.device_token] =
                    getPrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, "666666")
                viewModel.postuserloginApi(this, true, hashMap)
                viewModel.homeResponse.observe(this, this)
            }


        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is UserLoginResponse) {
                    val mResponse: UserLoginResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        MyApplication.instance.setString("globalID",mResponse.body.id.toString())
                        when (mResponse.body.role) {
                            1 -> setData(mResponse)
                            2 -> { setDataDriver(mResponse) }
                            3 -> setDataVendor(mResponse)
                            4 -> setCommercialData(mResponse)
                            5 ->  setDataAdvertiser(mResponse) 
                        }
                        goingToHome()
                    } else {
                         Toast.makeText(this, mResponse.message,Toast.LENGTH_SHORT).show()
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

    private fun setCommercialData(data: UserLoginResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.token)
        MyApplication.instance.setString("usertype",data.body.role.toString())
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.token, data.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.id, data.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.role, data.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.verified, data.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.status, data.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.firstName, data.body.commercialDetail.firstName)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.lastName, data.body.commercialDetail.lastName)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.image, data.body.commercialDetail.image)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.email, data.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.mobile, data.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceType, data.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.notification, data.body.notification)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.remember_token, data.body.remember_token)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.created, data.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.updated, data.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.createdAt, data.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.updatedAt, data.body.updatedAt)
    }

    private fun setDataAdvertiser(data: UserLoginResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.token)
        MyApplication.instance.setString("usertype",data.body.role.toString())
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.token, data.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.id, data.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.role, data.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.verified, data.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.status, data.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.firstName, data.body.advertiserDetail.firstName)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.lastName, data.body.advertiserDetail.lastName)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.image, data.body.advertiserDetail.image)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.email, data.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.mobile, data.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.deviceType, data.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.notification, data.body.notification)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.remember_token, data.body.remember_token)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.created, data.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.updated, data.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.createdAt, data.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.updatedAt, data.body.updatedAt)
    }

    private fun setDataDriver(mResponse: UserLoginResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, mResponse.body.token)
        MyApplication.instance.setString("usertype",mResponse.body.role.toString())
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.DRIVER_DATA, modelToString(mResponse.body.driverDetail))
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.token, mResponse.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.id, mResponse.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.role, mResponse.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.verified, mResponse.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.status, mResponse.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.email, mResponse.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.mobile, mResponse.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.deviceToken, mResponse.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.deviceType, mResponse.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.notification, mResponse.body.notification)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.remember_token, mResponse.body.remember_token)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.created, mResponse.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.updated, mResponse.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.createdAt, mResponse.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_DRIVER.updatedAt, mResponse.body.updatedAt)
    }

    private fun setDataVendor(data: UserLoginResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.token)
        MyApplication.instance.setString("usertype",data.body.role.toString())
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
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
        savePrefrence(GlobalVariables.SHARED_PREF_VENDOR.remember_token, data.body.remember_token)
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
        savePrefrence(GlobalVariables.SHARED_PREF_USER.remember_token, mResponse.body.remember_token)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.created, mResponse.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.updated, mResponse.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.createdAt, mResponse.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_USER.updatedAt, mResponse.body.updatedAt)
    }

    override fun onSuccessFacebook(bundle: Bundle?) {
        LoginManager.getInstance().logOut()
        iv_fb.isEnabled = true
        iv_gmail.isEnabled = true
        val firstName = bundle!!.getString(FIRST_NAME)
        val lastName = bundle.getString(LAST_NAME)
        social_name= "$firstName $lastName"
        social_id = bundle.getString(FACEBOOK_ID)!!
        social_image=bundle.getString(PROFILE_PIC)!!

        if (bundle.getString(EMAIL)!=null){
            social_email =  bundle.getString(EMAIL)!!
            api("1",social_name,social_email,social_id,social_image)
        }

    }

    override fun onCancelFacebook() {
        iv_fb.isEnabled = true
        iv_gmail.isEnabled = true
        Log.e("facebook", "facebook:onCancel")
    }
    override fun onErrorFacebook(ex: FacebookException?) {

        iv_fb.isEnabled = true
        iv_gmail.isEnabled = true
        Log.d("facebook", "facebook:onError", ex)
    }

    private fun callGoogleApi(account: GoogleSignInAccount?) {
         Log.e("TAG", "firebaseAuthWithGoogle:" + account!!.id)
         Log.e("TAG", "firebaseAuthWithGoogle:" + account.displayName)
         social_id = account.id!!
         social_email = account.email!!
         social_name = account.displayName!!
         social_image = account.photoUrl!!.toString()
         socialLogin(account,"2")
    }
    private fun socialLogin(socialAuthId: GoogleSignInAccount?, socialType:String){

        if(socialAuthId!!.email==null){
            api(
                socialType,
                socialAuthId.displayName!!,
                socialAuthId.email!!,
                socialAuthId.id!!,
                social_image)
        }
        else{
            api(socialType,socialAuthId.displayName!!,
                socialAuthId.email!!,socialAuthId.id!!,
                social_image)
        }
        googleSignInClient!!.signOut()
        LoginManager.getInstance().logOut()
    }

    private fun api(socialType: String, displayName: String, email: String, uid: String, socialImage: Any) {}

}