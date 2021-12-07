package com.stalkstock.advertiser.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.CommercialSignUpResponse
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_addnewaddress.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.spinner
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.HashMap

class SignupAdvertiserNCommercialNVendor : BaseActivity(), View.OnClickListener,
    Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {

    private var latitude = ""
    private var longitude = ""
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var business_type = 0
    var business_delivery_type = 0
    var country = ""
    var city = ""
    var address = ""
    var geoLocation = ""
    var state = ""
    var postalCode = ""
    var knownName = ""
    override fun getContentId(): Int {
        return R.layout.activity_signup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Places.initialize(this, getString(R.string.maps_api_key))
        tv_heading.text = getString(R.string.sign_up)
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        et_businessAddress.setOnClickListener(this)
        spinner.onItemSelectedListener = this
        spinner_type.onItemSelectedListener = this
        spinner_delivery_type.onItemSelectedListener = this

        val foodadapter3: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_delivery_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter3.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_delivery_type.adapter = foodadapter3


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

            R.id.et_businessAddress -> {
                val fields = listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS_COMPONENTS,
                    Place.Field.ADDRESS
                )
                // Start the autocomplete intent.
                val intent =
                    Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(
                        this
                    )
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
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
        }else if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val place = Autocomplete.getPlaceFromIntent(data)
                latitude = place.latLng?.latitude.toString()
                longitude = place.latLng?.longitude.toString()
                getAddress(latitude.toDouble(),longitude.toDouble())
                et_businessAddress.setText(place.name.toString())

            }
        }
    }


    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )

        if (addresses[0].locality != null) {
            city = addresses[0].locality
            et_city.setText(city)
        }
        if (addresses[0].adminArea != null) {
            state = addresses[0].adminArea
            et_state.setText(state)
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
        } else if (business_type == 0) {
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
        } else if (spinner.selectedItem.toString() == "Select Country") {
            AppUtils.showErrorAlert(
                this,
                resources.getString(R.string.please_enter_country)
            )
        } else if (passwordEdittext.getText().toString().isEmpty()) {
            passwordEdittext.requestFocus()
            passwordEdittext.setError(resources.getString(R.string.please_enter_password))
        } else if (passwordEdittext.getText().toString().length < 6) {
            passwordEdittext.requestFocus()
            passwordEdittext.setError("Password should contain at least 6 characters")
        } else if (repasswordEdittext.getText().toString().isEmpty()) {
            repasswordEdittext.requestFocus()
            repasswordEdittext.setError(resources.getString(R.string.please_reenter_password))
        } else if (!passwordEdittext.getText().toString()
                .equals(repasswordEdittext.getText().toString())
        ) {
            repasswordEdittext.requestFocus()
            repasswordEdittext.setError(resources.getString(R.string.new_confirm_password_dont_match))
        } else if (MyApplication.instance.getString("usertype").equals("4")) {

            if (spinner.selectedItem.toString() == "Select Country") {
                Toast.makeText(
                    this,
                    resources.getString(R.string.please_enter_country),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val hashMap = HashMap<String, RequestBody>()
            hashMap["latitude"] = mUtils.createPartFromString(latitude)
            hashMap["longitude"] = mUtils.createPartFromString(longitude)
            hashMap[GlobalVariables.PARAM.firstname] =
                mUtils.createPartFromString(et_firstName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.lastname] =
                mUtils.createPartFromString(et_lastName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessName] =
                mUtils.createPartFromString(et_businessName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessDescription] =
                mUtils.createPartFromString(et_businessDescptn.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessTypeId] =
                mUtils.createPartFromString(spinner_type.selectedItemPosition.toString())
            hashMap[GlobalVariables.PARAM.buisnessLicense] =
                mUtils.createPartFromString(licnEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.deliveryType] =
                mUtils.createPartFromString((business_delivery_type - 1).toString())

            hashMap[GlobalVariables.PARAM.email] =
                mUtils.createPartFromString(emailEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.mobile] =
                mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            hashMap[GlobalVariables.PARAM.businessPhone] =
                mUtils.createPartFromString(et_businessPhone.text.toString().trim())
            hashMap[GlobalVariables.PARAM.website] =
                mUtils.createPartFromString(et_website.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessAddress] =
                mUtils.createPartFromString(et_businessAddress.text.toString().trim())
            hashMap[GlobalVariables.PARAM.city] =
                mUtils.createPartFromString(et_city.text.toString().trim())
            hashMap[GlobalVariables.PARAM.state] =
                mUtils.createPartFromString(et_state.text.toString().trim())
            hashMap[GlobalVariables.PARAM.postalCode] =
                mUtils.createPartFromString(et_zipCode.text.toString().trim())
            hashMap[GlobalVariables.PARAM.country] =
                mUtils.createPartFromString(spinner.selectedItem.toString())
            hashMap[GlobalVariables.PARAM.password] =
                mUtils.createPartFromString(passwordEdittext.text.toString().trim())


            viewModel.commrercialSignupApi(this, true, hashMap, firstimage, mUtils)
            viewModel.homeResponse.observe(this, this)
        } else {

            if (spinner.selectedItem.toString() == "Select Country") {
                Toast.makeText(
                    this,
                    resources.getString(R.string.please_enter_country),
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            val hashMap = HashMap<String, RequestBody>()
            hashMap[GlobalVariables.PARAM.firstname] = mUtils.createPartFromString(et_firstName.text.toString().trim())
            hashMap["latitude"] = mUtils.createPartFromString(latitude)
            hashMap["longitude"] = mUtils.createPartFromString(longitude)
            hashMap[GlobalVariables.PARAM.lastname] =
                mUtils.createPartFromString(et_lastName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopName] =
                mUtils.createPartFromString(et_businessName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopDescription] =
                mUtils.createPartFromString(et_businessDescptn.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessTypeId] =
                mUtils.createPartFromString(business_type.toString())
            hashMap[GlobalVariables.PARAM.deliveryType] =
                mUtils.createPartFromString((business_delivery_type - 1).toString())
            hashMap[GlobalVariables.PARAM.buisnessLicense] =
                mUtils.createPartFromString(licnEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.email] =
                mUtils.createPartFromString(emailEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.mobile] =
                mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            hashMap[GlobalVariables.PARAM.businessPhone] =
                mUtils.createPartFromString(et_businessPhone.text.toString().trim())
            hashMap[GlobalVariables.PARAM.website] =
                mUtils.createPartFromString(et_website.text.toString().trim())
            hashMap[GlobalVariables.PARAM.shopAddress] =
                mUtils.createPartFromString(et_businessAddress.text.toString().trim())
            hashMap[GlobalVariables.PARAM.city] =
                mUtils.createPartFromString(et_city.text.toString().trim())
            hashMap[GlobalVariables.PARAM.state] =
                mUtils.createPartFromString(et_state.text.toString().trim())
            hashMap[GlobalVariables.PARAM.postalCode] =
                mUtils.createPartFromString(et_zipCode.text.toString().trim())
            hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(country)
            hashMap[GlobalVariables.PARAM.password] =
                mUtils.createPartFromString(passwordEdittext.text.toString().trim())

            viewModel.postvendorsignupApi(this, true, hashMap, firstimage, mUtils)
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
                        AppUtils.showSuccessAlert(this,"Sign up successfully !! please login to continue")
                        Handler(Looper.getMainLooper()).postDelayed({
                            startActivity(Intent(this, LoginActivity::class.java))
                            //Do something after 100ms
                        }, 2000)
                        //startActivity(Intent(mContext, Verification::class.java))
//                finish()
                    } else {
                        startActivity(Intent(mContext, LoginActivity::class.java))
                        finish()
                    }
                }

                if (it.data is CommercialSignUpResponse) {
                    val data = it.data as CommercialSignUpResponse
                    if (data.code == 200) {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_SHORT).show()
                        // setCommercialData(data)
                        startActivity(Intent(this, LoginActivity::class.java))
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
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setCommercialData(data: CommercialSignUpResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.authKey)
        savePrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.AUTH_KEY, data.body.authKey)
        savePrefrence(
            GlobalVariables.SHARED_PREF.USER_TYPE,
            MyApplication.instance.getString("usertype").toString()
        )
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.token, data.body.token)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.id, data.body.id)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.role, data.body.role)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.verified, data.body.verified)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.status, data.body.status)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.email, data.body.email)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.mobile, data.body.mobile)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceToken, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.deviceType, data.body.deviceType)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.notification, data.body.notification)
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.remember_token,
            data.body.remember_token
        )
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.created, data.body.created)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.updated, data.body.updated)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.createdAt, data.body.createdAt)
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.updatedAt, data.body.updatedAt)
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.advertiserId,
            data.body.commercialDetail.id
        )

        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.firstName,
            data.body.commercialDetail.firstName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.lastName,
            data.body.commercialDetail.lastName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.image,
            data.body.commercialDetail.image
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessPhone,
            data.body.commercialDetail.buisnessPhone
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessLogo,
            data.body.commercialDetail.buisnessLogo
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessTypeId,
            data.body.commercialDetail.buisnessTypeId
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessName,
            data.body.commercialDetail.buisnessName
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessLicense,
            data.body.commercialDetail.buisnessLicense
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.website,
            data.body.commercialDetail.website
        )
        savePrefrence(GlobalVariables.SHARED_PREF_COMMERCIAL.city, data.body.commercialDetail.city)
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.state,
            data.body.commercialDetail.state
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.country,
            data.body.commercialDetail.country
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.postalCode,
            data.body.commercialDetail.postalCode
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessAddress,
            data.body.commercialDetail.buisnessAddress
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.addressLine2,
            data.body.commercialDetail.addressLine2
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.buisnessDescription,
            data.body.commercialDetail.buisnessDescription
        )
        savePrefrence(
            GlobalVariables.SHARED_PREF_COMMERCIAL.userId,
            data.body.commercialDetail.userId
        )
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


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner) {
            var array = this.resources.getStringArray(R.array.Select_country)

            country = array[p2]
        } else if (p0?.id == R.id.spinner_type) {
            var array = this.resources.getStringArray(R.array.Select_business_type)

            business_type = p2
        } else if (p0?.id == R.id.spinner_delivery_type) {
            var array = this.resources.getStringArray(R.array.Select_business_delivery_type)

            business_delivery_type = p2
        }
    }
}