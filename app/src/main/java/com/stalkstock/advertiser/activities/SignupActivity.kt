package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.database.Observable
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.net.Status
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.commercial.view.activities.Verification
import com.stalkstock.consumer.activities.SelectuserActivity
import com.stalkstock.response_models.vendor_response.vendor_signup.VendorSignupResponse
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.net.RestObservable
import com.tamam.utils.others.GlobalVariables
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.emailEdittext
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.HashMap

class SignupActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>,
    AdapterView.OnItemSelectedListener {

    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    val mContext: Context = this
    private var mAlbumFiles: java.util.ArrayList<AlbumFile> = java.util.ArrayList()
    var firstimage = ""
    var business_type = ""
    var country = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_signup)
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

        if (et_firstName.getText().toString().isEmpty()) {
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
        } else if (licnEdittext.getText().toString().isEmpty()) {
            licnEdittext.requestFocus()
            licnEdittext.setError(resources.getString(R.string.please_enter_business_license))
        } else if (emailEdittext.getText().toString().isEmpty()) {
            emailEdittext.requestFocus()
            emailEdittext.setError(resources.getString(R.string.please_enter_email))
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.getText().toString()).matches()) {
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
        } else if (firstimage.isEmpty()) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_image),
                Toast.LENGTH_LONG
            ).show()

        } else {

            val hashMap = HashMap<String, String>()
            hashMap[GlobalVariables.PARAM.firstname] = et_firstName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.lastname] = et_lastName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.shopName] = et_businessName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.shopDescription] =
                et_businessDescptn.text.toString().trim()
            hashMap[GlobalVariables.PARAM.buisnessTypeId] = business_type
            hashMap[GlobalVariables.PARAM.buisnessLicense] = licnEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.mobile] = et_mobileNo.text.toString().trim()
            hashMap[GlobalVariables.PARAM.businessPhone] = et_businessPhone.text.toString().trim()
            hashMap[GlobalVariables.PARAM.website] = et_website.text.toString().trim()
            hashMap[GlobalVariables.PARAM.shopAddress] = et_businessAddress.text.toString().trim()
            hashMap[GlobalVariables.PARAM.city] = et_city.text.toString().trim()
            hashMap[GlobalVariables.PARAM.state] = et_state.text.toString().trim()
            hashMap[GlobalVariables.PARAM.postalCode] = et_zipCode.text.toString().trim()
            hashMap[GlobalVariables.PARAM.country] = country
            hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()

            viewModel.postvendorsignupApi(this, true, hashMap)
            viewModel.homeResponse.observe(this, this)

        }

    }

    override fun onChanged(t: RestObservable?) {
        when (t?.status) {

            Status.SUCCESS -> {
                if (t.data is VendorSignupResponse) {
                    if (MyApplication.instance.getString("usertype").equals("2")) {

                        var intent = Intent(mContext, Verification::class.java)
                        startActivityForResult(intent, 125)
                        //startActivity(Intent(mContext, Verification::class.java))
//                finish()
                    } else {
                        startActivity(Intent(mContext, LoginActivity::class.java))
                        finish()
                    }
                }

            }

        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner) {
            var array = this.resources.getStringArray(R.array.Select_country)

            country = array.get(p2)
        } else if (p0?.id == R.id.spinner_type) {
            var array = this.resources.getStringArray(R.array.Select_business_type)

            business_type = array.get(p2)
        }
    }
}