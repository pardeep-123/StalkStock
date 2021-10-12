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
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.advertiser.model.AdvertiserSignUpResponse
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody

class SignUpActivity: BaseActivity(), View.OnClickListener,
    Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {

    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }

    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var business_type = ""
    var country = ""

    override fun getContentId(): Int {
        return R.layout.activity_signup
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


          relAddressLine.visibility = View.GONE


        tv_heading.text = getString(R.string.sign_up)
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)
        spinner.onItemSelectedListener = this
        spinner_type.onItemSelectedListener = this


        val foodAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodAdapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodAdapter


        val foodAdapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.Select_business_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodAdapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_type.adapter = foodAdapter2

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
        } else if (spinner_type.selectedItemPosition == 0) {
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_business_type))
        }
        else if (spinner.selectedItemPosition == 0){
            AppUtils.showErrorAlert(this, resources.getString(R.string.please_enter_country))
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
        } else if (passwordEdittext.text.toString().isEmpty()) {
            passwordEdittext.requestFocus()
            passwordEdittext.error = resources.getString(R.string.please_enter_password)
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
            hashMap[GlobalVariables.PARAM.buisnessDescription] =
                mUtils.createPartFromString(et_businessDescptn.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessTypeId] = mUtils.createPartFromString(spinner_type.selectedItemPosition.toString())
            hashMap[GlobalVariables.PARAM.buisnessLicense] = mUtils.createPartFromString(licnEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.email] = mUtils.createPartFromString(emailEdittext.text.toString().trim())
            hashMap[GlobalVariables.PARAM.mobile] = mUtils.createPartFromString(et_mobileNo.text.toString().trim())
            hashMap[GlobalVariables.PARAM.businessPhone] =
                mUtils.createPartFromString(et_businessPhone.text.toString().trim())
            hashMap[GlobalVariables.PARAM.website] = mUtils.createPartFromString(et_website.text.toString().trim())
            hashMap[GlobalVariables.PARAM.buisnessAddress] =
                mUtils.createPartFromString(et_businessAddress.text.toString().trim())
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner) {
            val array = this.resources.getStringArray(R.array.Select_country)

            country = array[p2]
        } else if (p0?.id == R.id.spinner_type) {
            var array = this.resources.getStringArray(R.array.Select_business_type)

            business_type = p2.toString()
        }
    }
}