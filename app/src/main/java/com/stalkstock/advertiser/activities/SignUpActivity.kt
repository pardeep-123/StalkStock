package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
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
import com.stalkstock.advertiser.model.AdvertiserSignUpResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.adapters.CategoryCommercialAdapter
import com.stalkstock.commercial.view.model.CategoryList
import com.stalkstock.common.MyNewMapActivity
import com.stalkstock.common.model.ModelBusinessType
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.stalkstock.vender.Utils.NetworkUtil
import com.stalkstock.viewmodel.HomeViewModel
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_edit_business_profile.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.spinner
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.HashMap

class SignUpActivity: BaseActivity(), View.OnClickListener,
    Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    val PERMISSION_CALLBACK_CONSTANT = 100
    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }
    val viewModel1: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    val listC: ArrayList<CategoryList> = ArrayList()
    var businessTypeList: ArrayList<ModelBusinessType.Body> = ArrayList()
    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var businessType = ""
    var country = ""
    var city = ""
    var address = ""
    var geoLocation = ""
    var state = ""
    var postalCode = ""
    var knownName = ""
    private var latitude = ""
    private var longitude = ""

    override fun getContentId(): Int {
        return R.layout.activity_signup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Places.initialize(this, getString(R.string.maps_api_key))
        relAddressLine.visibility = View.VISIBLE
        getBusinessTypeApi()

        tv_heading.text = getString(R.string.sign_up)
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        et_businessAddress.setOnClickListener(this)
        spinner.onItemSelectedListener = this
        spinner_business_type.onItemSelectedListener = this

        val countryAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        countryAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = countryAdapter

        val businessTypeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_type,
            R.layout.spinner_layout_for_vehicle
        )
        businessTypeAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_business_type.adapter = businessTypeAdapter
    }


    private fun getBusinessTypeApi() {
        viewModel1.getBusinessTypeApi(this, true)
        viewModel1.homeResponse.observe(this, this)
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
                setValidation()
            }
            R.id.iv_back -> {
                finish()
            }
            R.id.et_businessAddress ->{
                if (NetworkUtil.checkLocPermission(this@SignUpActivity)) {
                    val intent = Intent(this, MyNewMapActivity::class.java)
                    intent.putExtra("type", "1")
                    startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

                }
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
       else if (data != null) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
                val sarea = data.getStringExtra("area")
                val myCity = data.getStringExtra("city")
                latitude = data.getStringExtra("lat")!!.toString()
                longitude = data.getStringExtra("lng")!!.toString()
                val state = data.getStringExtra("state")
                //   autoTvLocation.setText(sarea)
                //  et_street.setText(state)
                //  et_city.setText(myCity)
                getAddress(latitude.toDouble(),longitude.toDouble())
            }
        }
       /* else if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val place = Autocomplete.getPlaceFromIntent(data)
                latitude = place.latLng?.latitude.toString()
                longitude = place.latLng?.longitude.toString()
                getAddress(latitude.toDouble(),longitude.toDouble())
                et_businessAddress.setText(place.name.toString())

            }
        }*/
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            val intent = Intent(this, MyNewMapActivity::class.java)
            intent.putExtra("type", "1")
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
        }
    }
    private fun getAddress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())

        val addresses: List<Address> = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )
        if (addresses[0].featureName != null) {
            address = addresses[0].featureName
            et_businessAddress.setText(address)
        }

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
                if (type == "1") {
                    firstimage = result[0].path
                }
            }
            .onCancel {
            }
            .start()
    }

    private fun setValidation() {
        if (firstimage.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_image),
                Toast.LENGTH_LONG
            ).show()
        } else if (et_firstName.text.toString().isEmpty()) {
            et_firstName.requestFocus()
            et_firstName.error = resources.getString(R.string.please_enter_first_name)
        } else if (et_lastName.text.toString().isEmpty()) {
            et_lastName.requestFocus()
            et_lastName.error = resources.getString(R.string.please_enter_last_name)
        } else if (et_businessName.text.toString().isEmpty()) {
            et_businessName.requestFocus()
            et_businessName.error = resources.getString(R.string.please_enter_business_name)
        } else if (et_businessDescptn.text.toString().isEmpty()) {
            et_businessDescptn.requestFocus()
            et_businessDescptn.error = resources.getString(R.string.please_enter_business_description)
        } else if (spinner_business_type.selectedItemPosition == 0) {
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_business_type))
        }

        else if (licnEdittext.text.toString().isEmpty()) {
            licnEdittext.requestFocus()
            licnEdittext.error = resources.getString(R.string.please_enter_business_license)
        } else if (emailEdittext.text.toString().isEmpty()) {
            emailEdittext.requestFocus()
            emailEdittext.error = resources.getString(R.string.please_enter_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.getText().toString())
                .matches()
        ) {
            emailEdittext.requestFocus()
            emailEdittext.error = resources.getString(R.string.please_enter_valid_email)
        } else if (et_mobileNo.text.toString().isEmpty()) {
            et_mobileNo.requestFocus()
            et_mobileNo.error = resources.getString(R.string.please_enter_mobile_number)
        } else if (et_mobileNo.text.toString().length < 10 || et_mobileNo.getText()
                .toString().length > 13
        ) {
            et_mobileNo.requestFocus()
            et_mobileNo.error = resources.getString(R.string.please_enter_valid_number)
        } else if (et_businessPhone.text.toString().isEmpty()) {
            et_businessPhone.requestFocus()
            et_businessPhone.error = resources.getString(R.string.please_enter_business_phone_number)
        } else if (et_businessPhone.text.toString().length < 6) {
            et_businessPhone.requestFocus()
            et_businessPhone.error = resources.getString(R.string.please_enter_valid_number)
        } else if (et_website.text.toString().isEmpty()) {
            et_website.requestFocus()
            et_website.error = resources.getString(R.string.please_enter_website)
        } else if (et_businessAddress.text.toString().isEmpty()) {
            et_businessAddress.requestFocus()
            et_businessAddress.error = resources.getString(R.string.please_enter_business_address)
        } else if (et_city.text.toString().isEmpty()) {
            et_city.requestFocus()
            et_city.error = resources.getString(R.string.please_enter_city)
        } else if (et_state.text.toString().isEmpty()) {
            et_state.requestFocus()
            et_state.error = resources.getString(R.string.please_enter_state)
        } else if (et_zipCode.text.toString().isEmpty()) {
            et_zipCode.requestFocus()
            et_zipCode.error = resources.getString(R.string.please_enter_postal_code)
        }  else if (spinner.selectedItemPosition == 0){
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_country))
        } else if (passwordEdittext.text.toString().isEmpty()) {
            passwordEdittext.requestFocus()
            passwordEdittext.error = resources.getString(R.string.please_enter_password)
        }else if (passwordEdittext.text.toString().length<6) {
            passwordEdittext.requestFocus()
            passwordEdittext.error = "Password should contain atleast 6 characters"
        } else if (repasswordEdittext.text.toString().isEmpty()) {
            repasswordEdittext.requestFocus()
            repasswordEdittext.error = resources.getString(R.string.please_reenter_password)
        } else if (passwordEdittext.text.toString() != repasswordEdittext.text.toString()
        ) {
            repasswordEdittext.requestFocus()
            repasswordEdittext.error = resources.getString(R.string.new_confirm_password_dont_match)
        } else {

            val hashMap = HashMap<String, RequestBody>()
            hashMap[GlobalVariables.PARAM.firstname] = mUtils.createPartFromString(et_firstName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.lastname] = mUtils.createPartFromString(et_lastName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessName] = mUtils.createPartFromString(et_businessName.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessDescription] = mUtils.createPartFromString(et_businessDescptn.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessTypeId] = mUtils.createPartFromString(spinner_business_type.selectedItemPosition.toString())
            hashMap[GlobalVariables.PARAM.buisnessLicense] = mUtils.createPartFromString(licnEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.email] = mUtils.createPartFromString(emailEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.mobile] = mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            hashMap[GlobalVariables.PARAM.businessPhone] = mUtils.createPartFromString(et_businessPhone.text.toString().trim())
            hashMap[GlobalVariables.PARAM.website] = mUtils.createPartFromString(et_website.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessAddress] = mUtils.createPartFromString(et_businessAddress.text.toString().trim())
            hashMap["addressLine2"] = mUtils.createPartFromString(et_addressline2.text.toString())
            hashMap[GlobalVariables.PARAM.city] = mUtils.createPartFromString(et_city.text.toString().trim())
            hashMap[GlobalVariables.PARAM.state] = mUtils.createPartFromString(et_state.text.toString().trim())
            hashMap[GlobalVariables.PARAM.postalCode] = mUtils.createPartFromString(et_zipCode.text.toString().trim())
            hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(spinner.selectedItem.toString())
            hashMap[GlobalVariables.PARAM.password] = mUtils.createPartFromString(passwordEdittext.text.toString().trim())

            viewModel.postAdvertiserSignUpApi(this, true, hashMap,firstimage,mUtils)
            viewModel.mResponse.observe(this, this)
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is AdvertiserSignUpResponse) {
                    val data = it.data
                    if (data.code==200){
                        setData(data)
                        startActivity(Intent(this,MainActivity::class.java))
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
                                        businessTypeList[i].name)
                                )
                            }
                        }

                        val businessTypeListt = CategoryCommercialAdapter(this, "-Select your business type-", listC)
                        spinner_business_type.adapter = businessTypeListt
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

    private fun setData(data: AdvertiserSignUpResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, data.body.authKey)
        savePrefrence(GlobalVariables.SHARED_PREF.DEVICE_TOKEN, data.body.deviceToken)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.AUTH_KEY, data.body.authKey)
        savePrefrence(GlobalVariables.SHARED_PREF.USER_TYPE, MyApplication.instance.getString("usertype").toString())
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
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.advertiserId, data.body.advertiserDetail.id)

        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.firstName,
            data.body.advertiserDetail.firstName
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.lastName, data.body.advertiserDetail.lastName)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.image, data.body.advertiserDetail.image)
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessPhone,
            data.body.advertiserDetail.buisnessPhone
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.buisnessLogo, data.body.advertiserDetail.buisnessLogo)
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessTypeId,
            data.body.advertiserDetail.buisnessTypeId
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.buisnessName, data.body.advertiserDetail.buisnessName)
        savePrefrence(
            GlobalVariables.SHARED_PREF_ADVERTISER.buisnessLicense,
            data.body.advertiserDetail.buisnessLicense
        )
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.website, data.body.advertiserDetail.website)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.city, data.body.advertiserDetail.city)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.state, data.body.advertiserDetail.state)
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.country, data.body.advertiserDetail.country)
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
        savePrefrence(GlobalVariables.SHARED_PREF_ADVERTISER.userId, data.body.advertiserDetail.userId)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, selectedItemView: View?, p2: Int, p3: Long) {
        if (p2 == 0) {
            (selectedItemView as? TextView)?.setTextColor(
                ContextCompat.getColor(
                    this@SignUpActivity, R.color.black_color
                )
            )
        } else {
            (selectedItemView as? TextView)?.setTextColor(
                ContextCompat.getColor(
                    this@SignUpActivity, R.color.black_color
                )
            )
            if (p0?.id == R.id.spinner) {
                val array = this.resources.getStringArray(R.array.Select_country)

                country = array[p2]
            }
            else if (p0?.id == R.id.spinner_business_type) {
                val categories = businessTypeList[spinner_business_type!!.selectedItemPosition - 1]
                businessType = categories.id.toString()
            }
        }
    }
}