package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.vender.ui.BottomnavigationScreen
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody

class SignupAdvertiserNCommercialNVendor : BaseActivity(), View.OnClickListener,
    Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {

    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var business_type = "0"
    var country = ""
    override fun getContentId(): Int {
        return R.layout.activity_signup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        tv_heading.text = getString(R.string.sign_up)
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        spinner.onItemSelectedListener = this
        spinner_type.onItemSelectedListener = this


        val foodadapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter


        val foodadapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_type.adapter = foodadapter2


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_signin -> {
                startActivity(Intent(mContext, LoginActivity::class.java))
                finish()
            }
            R.id.total -> {
                startActivity(Intent(mContext, LoginActivity::class.java))
                finish()
            }
            R.id.btn_signup -> {
                SetValidation()


            }
            R.id.iv_back -> {
                finish()
            }

            R.id.image -> {
                mAlbumFiles = java.util.ArrayList()
                mAlbumFiles.clear()
                selectImage(image, "1")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 125) {
            if (resultCode == RESULT_OK) {
                try {
                    if (data!!.getStringExtra("status") != null) {
                        onBackPressed()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

    }

    private fun selectImage(ivProduct: ImageView, type: String) {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            //.selectCount(1)
            //.checkedList(mAlbumFiles)
            .widget(
                Widget.newDarkBuilder(this)
                    .title(getString(R.string.app_name))
                    .build()
            )
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProduct)
                if (type.equals("1")) {
                    firstimage = result[0].path
                }
            }
            .onCancel {

            }
            .start()
    }

    fun SetValidation() {
        if (firstimage.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_image),
                Toast.LENGTH_LONG
            ).show()

        } else if (et_firstName.getText().toString().isEmpty()) {
            et_firstName.requestFocus()
            et_firstName.setError(resources.getString(R.string.please_enter_first_name))
        } else if (et_lastName.getText().toString().isEmpty()) {
            et_lastName.requestFocus()
            et_lastName.setError(resources.getString(R.string.please_enter_last_name))
        } else if (et_businessName.getText().toString().isEmpty()) {
            et_businessName.requestFocus()
            et_businessName.setError(resources.getString(R.string.please_enter_business_name))
        } else if (et_businessDescptn.getText().toString().isEmpty()) {
            et_businessDescptn.requestFocus()
            et_businessDescptn.setError(resources.getString(R.string.please_enter_business_description))
        } else if (business_type.equals("0")) {
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_business_type))
        } else if (licnEdittext.getText().toString().isEmpty()) {
            licnEdittext.requestFocus()
            licnEdittext.setError(resources.getString(R.string.please_enter_business_license))
        } else if (emailEdittext.getText().toString().isEmpty()) {
            emailEdittext.requestFocus()
            emailEdittext.setError(resources.getString(R.string.please_enter_email))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.getText().toString())
                .matches()
        ) {
            emailEdittext.requestFocus()
            emailEdittext.setError(resources.getString(R.string.please_enter_valid_email))
        } else if (et_mobileNo.getText().toString().isEmpty()) {
            et_mobileNo.requestFocus()
            et_mobileNo.setError(resources.getString(R.string.please_enter_mobile_number))
        } else if (et_mobileNo.getText().toString().length < 10 || et_mobileNo.getText()
                .toString().length > 13
        ) {
            et_mobileNo.requestFocus()
            et_mobileNo.setError(resources.getString(R.string.please_enter_valid_number))
        } else if (et_businessPhone.getText().toString().isEmpty()) {
            et_businessPhone.requestFocus()
            et_businessPhone.setError(resources.getString(R.string.please_enter_business_phone_number))
        } else if (et_businessPhone.getText().toString().length < 6) {
            et_businessPhone.requestFocus()
            et_businessPhone.setError(resources.getString(R.string.please_enter_valid_number))
        } else if (et_website.getText().toString().isEmpty()) {
            et_website.requestFocus()
            et_website.setError(resources.getString(R.string.please_enter_website))
        } else if (et_businessAddress.getText().toString().isEmpty()) {
            et_businessAddress.requestFocus()
            et_businessAddress.setError(resources.getString(R.string.please_enter_business_address))
        } else if (et_city.getText().toString().isEmpty()) {
            et_city.requestFocus()
            et_city.setError(resources.getString(R.string.please_enter_city))
        } else if (et_state.getText().toString().isEmpty()) {
            et_state.requestFocus()
            et_state.setError(resources.getString(R.string.please_enter_state))
        } else if (et_zipCode.getText().toString().isEmpty()) {
            et_zipCode.requestFocus()
            et_zipCode.setError(resources.getString(R.string.please_enter_postal_code))
        } else if (passwordEdittext.getText().toString().isEmpty()) {
            passwordEdittext.requestFocus()
            passwordEdittext.setError(resources.getString(R.string.please_enter_password))
        } else if (repasswordEdittext.getText().toString().isEmpty()) {
            repasswordEdittext.requestFocus()
            repasswordEdittext.setError(resources.getString(R.string.please_reenter_password))
        } else if (!passwordEdittext.getText().toString()
                .equals(repasswordEdittext.getText().toString())
        ) {
            repasswordEdittext.requestFocus()
            repasswordEdittext.setError(resources.getString(R.string.new_confirm_password_dont_match))
        } else {

            val hashMap = HashMap<String, RequestBody>()
            hashMap[GlobalVariables.PARAM.firstname] = mUtils.createPartFromString(et_firstName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.lastname] = mUtils.createPartFromString(et_lastName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopName] = mUtils.createPartFromString(et_businessName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopDescription] =
                mUtils.createPartFromString(et_businessDescptn.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessTypeId] = mUtils.createPartFromString(business_type)
            hashMap[GlobalVariables.PARAM.buisnessLicense] = mUtils.createPartFromString(licnEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.email] = mUtils.createPartFromString(emailEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.mobile] = mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            hashMap[GlobalVariables.PARAM.businessPhone] =
                mUtils.createPartFromString(et_businessPhone.text.toString().trim())
            hashMap[GlobalVariables.PARAM.website] = mUtils.createPartFromString(et_website.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopAddress] =
                mUtils.createPartFromString(et_businessAddress.text.toString().trim())
            hashMap[GlobalVariables.PARAM.city] = mUtils.createPartFromString(et_city.text.toString().trim())
            hashMap[GlobalVariables.PARAM.state] = mUtils.createPartFromString(et_state.text.toString().trim())
            hashMap[GlobalVariables.PARAM.postalCode] = mUtils.createPartFromString(et_zipCode.text.toString().trim())
            hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(country)
            hashMap[GlobalVariables.PARAM.password] = mUtils.createPartFromString(passwordEdittext.text.toString().trim())

            viewModel.postvendorsignupApi(this, true, hashMap,firstimage,mUtils)
            viewModel.homeResponse.observe(this, this)

        }

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorSignupResponse) {
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

    private fun setData(data: VendorSignupResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.token)
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


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner) {
            var array = this.resources.getStringArray(R.array.Select_country)

            country = array.get(p2)
        } else if (p0?.id == R.id.spinner_type) {
            var array = this.resources.getStringArray(R.array.Select_business_type)

            business_type = p2.toString()
        }
    }
}