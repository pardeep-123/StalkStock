package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.libraries.places.api.Places
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.pojo.SendOtpResponse
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.adapters.CategoryCommercialAdapter
import com.stalkstock.commercial.view.model.CategoryList
import com.stalkstock.commercial.view.model.CommercialSignUpResponse
import com.stalkstock.common.MyNewMapActivity
import com.stalkstock.common.model.ModelBusinessType
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.vender.Utils.NetworkUtil
import com.stalkstock.vender.ui.Verification
import com.stalkstock.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody
import java.util.*

class SignupAdvertiserNCommercialNVendor : BaseActivity(), View.OnClickListener,
    Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {

    private var latitude = ""
    private var longitude = ""
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    val PERMISSION_CALLBACK_CONSTANT = 100
    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    val listC: ArrayList<CategoryList> = ArrayList()
    var businessTypeList: ArrayList<ModelBusinessType.Body> = ArrayList()

    var otp = ""
    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    private var coverImage = ""
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

        if (MyApplication.instance.getString("usertype").equals("4")) {
            rlDelivery.visibility = View.GONE
            rlBusinessType.visibility = View.VISIBLE
        } else {
            rlDelivery.visibility = View.VISIBLE
            rlBusinessType.visibility = View.VISIBLE
        }

        if (MyApplication.instance.getString("usertype").equals("3")) {
            rlCoverPic.visibility = View.VISIBLE
        }

        getBusinessTypeApi()
        tv_heading.text = getString(R.string.sign_up)
        rl_deliveryType.visibility = View.VISIBLE
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        ivCoverImage.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        et_businessAddress.setOnClickListener(this)
        spinner.onItemSelectedListener = this
        spinner_business_type.onItemSelectedListener = this
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
        spinner_business_type.adapter = foodadapter2


    }

    private fun getBusinessTypeApi() {
        viewModel.getBusinessTypeApi(this, true)
        viewModel.homeResponse.observe(this, this)
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
            R.id.ivCoverImage -> {
                selectImage(ivCoverImage, "2")
            }

            R.id.et_businessAddress -> {
                if (NetworkUtil.checkLocPermission(this@SignupAdvertiserNCommercialNVendor)) {
                    val intent = Intent(this, MyNewMapActivity::class.java)
                    intent.putExtra("type", "1")
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            val intent = Intent(this, MyNewMapActivity::class.java)
            intent.putExtra("type", "1")
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
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
        } else if (data != null) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                val sarea = data.getStringExtra("place_name")
                val myCity = data.getStringExtra("city")
                latitude = data.getStringExtra("lat")!!.toString()
                longitude = data.getStringExtra("lng")!!.toString()
                val state = data.getStringExtra("state")
                et_businessAddress.setText(sarea)
                //  et_street.setText(state)
                //  et_city.setText(myCity)
                getAddress(latitude.toDouble(), longitude.toDouble())
            }
        }
        /* else if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
             if (resultCode == Activity.RESULT_OK && data != null) {
                 val place = Autocomplete.getPlaceFromIntent(data)
                 latitude = place.latLng?.latitude.toString()
                 longitude = place.latLng?.longitude.toString()
                 getAddress(latitude.toDouble(), longitude.toDouble())
                 et_businessAddress.setText(place.name.toString())

             }
         }*/
    }


    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )

        /* if (addresses[0].featureName != null) {
             address = addresses[0].featureName
             et_businessAddress.setText(address)
         }*/

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
                if (type.equals("1")) {
                    Glide.with(this).load(result[0].path).into(ivProduct)
                    firstimage = result[0].path
                } else if (type.equals("2")) {
                    Glide.with(this).load(result[0].path).into(ivProduct)
                    coverImage = result[0].path
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
        } else if (MyApplication.instance.getString("usertype") != "4" && spinner_delivery_type.selectedItemPosition == 0) {

            AppUtils.showErrorAlert(
                this,
                resources.getString(R.string.please_enter_business_delivery_type)
            )
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
        } else if (spinner.selectedItemPosition == 0) {
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

            /*val hashMap = HashMap<String, RequestBody>()
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
                mUtils.createPartFromString(spinner_business_type.selectedItemPosition.toString())
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


            viewModel.commrercialSignupApi(this, true, hashMap, firstimage, mUtils)*/
            // viewModel.homeResponse.observe(this, this)


            if (spinner.selectedItem.toString() == "Select Country") {
                Toast.makeText(
                    this,
                    resources.getString(R.string.please_enter_country),
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            val hashMap = HashMap<String, RequestBody>()
            hashMap["mobile"] = mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            viewModel.sendOtp(this, true, hashMap)


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
            hashMap["mobile"] = mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            viewModel.sendOtp(this, true, hashMap)
            // viewModel.homeResponse.observe(this, this)

        }

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

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
                if (it.data is ModelBusinessType) {
                    val mResponse: ModelBusinessType = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        businessTypeList.clear()
                        businessTypeList.addAll(mResponse.body)

                        listC.clear()
                        listC.add(CategoryList(0, 0, "-Select your business type-", ""))
                        if (businessTypeList.isNotEmpty()) {
                            for (i in 0 until businessTypeList.size) {
                                listC.add(
                                    CategoryList(
                                        businessTypeList[i].id, businessTypeList[i].status,
                                        businessTypeList[i].name
                                    )
                                )
                            }
                        }

                        val businessTypeListt =
                            CategoryCommercialAdapter(this, "-Select your business type-", listC)
                        spinner_business_type.adapter = businessTypeListt
                    }
                }

                if (it.data is SendOtpResponse) {
                    val data = it.data as SendOtpResponse
                    if (data.code == 200) {
                        Log.i("====", data.message)
                        otp = data.body.otp.toString()
                        if (MyApplication.instance.getString("usertype").equals("3")) {
                            val intent = Intent(this, Verification::class.java)
                            intent.putExtra("latitude", latitude)
                            intent.putExtra("longitude", longitude)
                            intent.putExtra("firstName", et_firstName.text.toString().trim())
                            intent.putExtra("lastname", et_lastName.text.toString().trim())
                            intent.putExtra("shopName", et_businessName.text.toString().trim())
                            intent.putExtra(
                                "shopDescription",
                                et_businessDescptn.text.toString().trim()
                            )
                            intent.putExtra("buisnessTypeId", business_type.toString())
                            intent.putExtra("deliveryType", (business_delivery_type - 1).toString())
                            intent.putExtra("buisnessLicense", licnEdittext.text.toString().trim())
                            intent.putExtra("email", emailEdittext.text.toString().trim())
                            intent.putExtra("mobile", et_mobileNo.text.toString().trim())
                            intent.putExtra(
                                "buisnessPhone",
                                et_businessPhone.text.toString().trim()
                            )
                            intent.putExtra("website", et_website.text.toString().trim())
                            intent.putExtra(
                                "shopAddress",
                                et_businessAddress.text.toString().trim()
                            )
                            intent.putExtra("city", et_city.text.toString().trim())
                            intent.putExtra("state", et_state.text.toString().trim())
                            intent.putExtra("postalCode", et_zipCode.text.toString().trim())
                            intent.putExtra("country", country)
                            intent.putExtra("password", passwordEdittext.text.toString().trim())
                            intent.putExtra("firstimage", firstimage)
                            intent.putExtra("coverImage", coverImage)
                            intent.putExtra("otp", otp)
                            startActivity(intent)
                        } else if (MyApplication.instance.getString("usertype").equals("4")) {
                            val intent = Intent(this, Verification::class.java)
                            intent.putExtra("latitude", latitude)
                            intent.putExtra("longitude", longitude)
                            intent.putExtra("firstName", et_firstName.text.toString().trim())
                            intent.putExtra("lastname", et_lastName.text.toString().trim())
                            intent.putExtra("buisnessName", et_businessName.text.toString().trim())
                            intent.putExtra(
                                "buisnessDescription",
                                et_businessDescptn.text.toString().trim()
                            )
                            intent.putExtra(
                                "buisnessTypeId",
                                spinner_business_type.selectedItemPosition.toString()
                            )
                            intent.putExtra("buisnessLicense", licnEdittext.text.toString().trim())
                            intent.putExtra("deliveryType", (business_delivery_type - 1).toString())
                            intent.putExtra("email", emailEdittext.text.toString().trim())
                            intent.putExtra("mobile", et_mobileNo.text.toString().trim())
                            intent.putExtra(
                                "businessPhone",
                                et_businessPhone.text.toString().trim()
                            )
                            intent.putExtra("website", et_website.text.toString().trim())
                            intent.putExtra(
                                "buisnessAddress",
                                et_businessAddress.text.toString().trim()
                            )
                            intent.putExtra("city", et_city.text.toString().trim())
                            intent.putExtra("state", et_state.text.toString().trim())
                            intent.putExtra("postalCode", et_zipCode.text.toString().trim())
                            intent.putExtra("country", spinner.selectedItem.toString())
                            intent.putExtra("password", passwordEdittext.text.toString().trim())
                            intent.putExtra("firstimage", firstimage)
                            intent.putExtra("otp", otp)
                            startActivity(intent)
                        }

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

    override fun onItemSelected(
        p0: AdapterView<*>?,
        selectedItemView: View?,
        position: Int,
        p3: Long
    ) {
        if (position == 0) {
            (selectedItemView as? TextView)?.setTextColor(
                ContextCompat.getColor(
                    this@SignupAdvertiserNCommercialNVendor, R.color.black_color
                )
            )
        } else {

            (selectedItemView as? TextView)?.setTextColor(
                ContextCompat.getColor(
                    this@SignupAdvertiserNCommercialNVendor, R.color.black_color
                )
            )
            if (p0?.id == R.id.spinner) {
                var array = this.resources.getStringArray(R.array.Select_country)
                country = array[position]
            } else if (p0?.id == R.id.spinner_business_type) {
                val categories = businessTypeList[spinner_business_type!!.selectedItemPosition - 1]
                business_type = categories.id
            } else if (p0?.id == R.id.spinner_delivery_type) {
                var array = this.resources.getStringArray(R.array.Select_business_delivery_type)

                business_delivery_type = position
            }
        }
    }
}