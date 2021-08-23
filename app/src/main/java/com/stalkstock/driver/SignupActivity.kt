package com.stalkstock.driver

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.CheckEmailResponse
import com.stalkstock.driver.models.DriverSignUpResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.others.CommonMethods
import com.stalkstock.utils.others.GlobalVariables
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup3.*
import kotlinx.android.synthetic.main.activity_signup3.btn_signup
import kotlinx.android.synthetic.main.activity_signup3.emailEdittext
import kotlinx.android.synthetic.main.activity_signup3.et_city
import kotlinx.android.synthetic.main.activity_signup3.et_firstName
import kotlinx.android.synthetic.main.activity_signup3.et_lastName
import kotlinx.android.synthetic.main.activity_signup3.et_mobileNo
import kotlinx.android.synthetic.main.activity_signup3.et_state
import kotlinx.android.synthetic.main.activity_signup3.image
import kotlinx.android.synthetic.main.activity_signup3.passwordEdittext
import kotlinx.android.synthetic.main.activity_signup3.repasswordEdittext
import kotlinx.android.synthetic.main.activity_signup3.spinner
import kotlinx.android.synthetic.main.activity_signup3.total
import kotlinx.android.synthetic.main.activity_signup3.tv_signin
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody


class SignupActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener,
    Observer<RestObservable> {
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var mVehicleType = ""
    var mCountryName = ""

    override fun getContentId(): Int {
        return R.layout.activity_signup3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        tv_heading.text = getString(R.string.sign_up)

        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)

        CommonMethods.hideKeyboard(this, btn_signup)
        // addItemsOnSpinner2();
        val foodadapter = ArrayAdapter.createFromResource(
            this,
            R.array.Select_Vehicle_type,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner.adapter = foodadapter


        val foodadapter2 = ArrayAdapter.createFromResource(
            this,
            R.array.Select_country,
            R.layout.spinner_layout_for_vehicle
        )
        foodadapter2.setDropDownViewResource(R.layout.spiner_layout_text)
        spinner_country.adapter = foodadapter2
        spinner_country.onItemSelectedListener = this
        spinner.onItemSelectedListener = this
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_signin -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.tv_signin -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.total -> {
                startActivity(Intent(this, LoginActivity::class.java))
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

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner_country) {
            val array = this.resources.getStringArray(R.array.Select_country)
            mCountryName = array.get(p2)
        } else if (p0?.id == R.id.spinner) {
            val array = this.resources.getStringArray(R.array.Select_Vehicle_type)
            mVehicleType = array.get(p2)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

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

        } else if (et_firstName.getText().toString().isEmpty()) {
            et_firstName.requestFocus()
            et_firstName.setError(resources.getString(R.string.please_enter_first_name))
        } else if (et_lastName.getText().toString().isEmpty()) {
            et_lastName.requestFocus()
            et_lastName.setError(resources.getString(R.string.please_enter_last_name))
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
        } else if (checkStringNull(mVehicleType)) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_vehicletype),
                Toast.LENGTH_LONG
            ).show()
        } else if (checkStringNull(et_vehiclemake.getText().toString())) {
            et_vehiclemake.requestFocus()
            et_vehiclemake.setError(resources.getString(R.string.please_enter_vehicle_make))
        } else if (checkStringNull(et_vehiclemodel.getText().toString())) {
            et_vehiclemodel.requestFocus()
            et_vehiclemodel.setError(resources.getString(R.string.please_enter_vehicle_model))
        } else if (checkStringNull(et_city.getText().toString())) {
            et_city.requestFocus()
            et_city.setError(resources.getString(R.string.please_enter_city))
        } else if (checkStringNull(et_state.getText().toString())) {
            et_state.requestFocus()
            et_state.setError(resources.getString(R.string.please_enter_state))
        } else if (checkStringNull(mCountryName)) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_countryname),
                Toast.LENGTH_LONG
            ).show()
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

            /* val hashMap = HashMap<String, RequestBody>()
         hashMap[GlobalVariables.PARAM.firstname] = mUtils.createPartFromString(et_firstName.text.toString().trim())
           hashMap[GlobalVariables.PARAM.lastname] = mUtils.createPartFromString(et_lastName.text.toString().trim())
           hashMap[GlobalVariables.PARAM.email] = mUtils.createPartFromString(emailEdittext.text.toString().trim())
           hashMap[GlobalVariables.PARAM.mobile] = mUtils.createPartFromString(et_mobileNo.text.toString().trim())
           hashMap[GlobalVariables.PARAM.vehicleType] = mUtils.createPartFromString(mVehicleType)
           hashMap[GlobalVariables.PARAM.vehicleMake] = mUtils.createPartFromString(et_vehiclemake.text.toString().trim())
           hashMap[GlobalVariables.PARAM.vehicleModel] =
               mUtils.createPartFromString(et_vehiclemodel.text.toString().trim())
           hashMap[GlobalVariables.PARAM.city] = mUtils.createPartFromString(et_city.text.toString().trim())
           hashMap[GlobalVariables.PARAM.state] = mUtils.createPartFromString(et_state.text.toString().trim())
           hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(mCountryName)
           hashMap[GlobalVariables.PARAM.password] = mUtils.createPartFromString(passwordEdittext.text.toString().trim())*/
            val hashMap = HashMap<String, String>()
            hashMap[GlobalVariables.PARAM.firstname] = et_firstName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.lastname] = et_lastName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.mobile] = et_mobileNo.text.toString().trim()
            hashMap[GlobalVariables.PARAM.vehicleType] = mVehicleType
            hashMap[GlobalVariables.PARAM.vehicleMake] = et_vehiclemake.text.toString().trim()
            hashMap[GlobalVariables.PARAM.vehicleModel] =
                et_vehiclemodel.text.toString().trim()
            hashMap[GlobalVariables.PARAM.city] = et_city.text.toString().trim()
            hashMap[GlobalVariables.PARAM.state] = et_state.text.toString().trim()
            hashMap[GlobalVariables.PARAM.country] = mCountryName
            hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()

            checkEmailAndMobileExistAPI()
/*
            startActivity(Intent(this, UploadDocActivity::class.java)
                .putExtra("driverData",hashMap)
                .putExtra("profileImage",firstimage))
*/
        }

    }

    val viewModel: DriverViewModel by viewModels()

    lateinit var hashMap:HashMap<String,RequestBody>

    private fun checkEmailAndMobileExistAPI() {
        hashMap = HashMap<String, RequestBody>()
        hashMap[GlobalVariables.PARAM.email] =
            mUtils.createPartFromString(emailEdittext.text.toString())
        hashMap[GlobalVariables.PARAM.mobile] =
            mUtils.createPartFromString(et_mobileNo.text.toString())
        viewModel.checkEmailMobileExist(this, true, hashMap)
        viewModel.mResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is CheckEmailResponse) {
                    val data = it.data as CheckEmailResponse
                    if (data.code == 200) {
                        startActivity(
                            Intent(this, UploadDocActivity::class.java)
                                .putExtra("driverData", hashMap)
                                .putExtra("profileImage", firstimage)
                        )
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
}
