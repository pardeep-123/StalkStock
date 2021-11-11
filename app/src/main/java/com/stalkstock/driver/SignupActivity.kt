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
import com.stalkstock.commercial.view.model.Sendbidresponse
import com.stalkstock.driver.models.CheckEmailResponse
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
    private var mAlbumFiles: ArrayList<AlbumFile> = ArrayList()
    var firstimage = ""
    private var mVehicleType = ""
    private var mCountryName = ""

    override fun getContentId(): Int {
        return R.layout.activity_signup3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_heading.text = getString(R.string.sign_up)
        tv_signin.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        image.setOnClickListener(this)
        tv_signin.setOnClickListener(this)
        total.setOnClickListener(this)
        btn_signup.setOnClickListener(this)

        CommonMethods.hideKeyboard(this, btn_signup)
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

            R.id.total -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            R.id.btn_signup -> { setValidation()
            }
            R.id.iv_back -> { finish()
            }
            R.id.image -> {
                mAlbumFiles = java.util.ArrayList()
                mAlbumFiles.clear()
                selectImage(image, "1")
            } } }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (p0?.id == R.id.spinner_country) {
            val array = this.resources.getStringArray(R.array.Select_country)
            mCountryName = array[p2]
        } else if (p0?.id == R.id.spinner) {
            val array = this.resources.getStringArray(R.array.Select_Vehicle_type)
            mVehicleType = array[p2]
        } }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun selectImage(ivProduct: ImageView, type: String) {
        Album.image(this)
            .singleChoice()
            .camera(true)
            .columnCount(4)
            .widget(Widget.newDarkBuilder(this).title(getString(R.string.app_name)).build())
            .onResult { result ->
                mAlbumFiles.addAll(result)
                Glide.with(this).load(result[0].path).into(ivProduct)
                if (type == "1") { firstimage = result[0].path } }
            .onCancel {}
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
        } else if (emailEdittext.text.toString().isEmpty()) {
            emailEdittext.requestFocus()
            emailEdittext.error = resources.getString(R.string.please_enter_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdittext.text.toString())
                .matches()
        ) {
            emailEdittext.requestFocus()
            emailEdittext.error = resources.getString(R.string.please_enter_valid_email)
        } else if (et_mobileNo.text.toString().isEmpty()) {
            et_mobileNo.requestFocus()
            et_mobileNo.error = resources.getString(R.string.please_enter_mobile_number)
        } else if (et_mobileNo.text.toString().length < 10 || et_mobileNo.text
                .toString().length > 13
        ) {
            et_mobileNo.requestFocus()
            et_mobileNo.error = resources.getString(R.string.please_enter_valid_number)
        } else if (checkStringNull(mVehicleType)) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_vehicletype),
                Toast.LENGTH_LONG
            ).show()
        } else if (checkStringNull(et_vehiclemake.text.toString())) {
            et_vehiclemake.requestFocus()
            et_vehiclemake.error = resources.getString(R.string.please_enter_vehicle_make)
        } else if (checkStringNull(et_vehiclemodel.text.toString())) {
            et_vehiclemodel.requestFocus()
            et_vehiclemodel.error = resources.getString(R.string.please_enter_vehicle_model)
        } else if (checkStringNull(et_city.text.toString())) {
            et_city.requestFocus()
            et_city.error = resources.getString(R.string.please_enter_city)
        } else if (checkStringNull(et_state.text.toString())) {
            et_state.requestFocus()
            et_state.error = resources.getString(R.string.please_enter_state)
        } else if (checkStringNull(mCountryName)) {
            Toast.makeText(
                this,
                resources.getString(R.string.please_select_countryname),
                Toast.LENGTH_LONG
            ).show()
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

            val hashMap = HashMap<String, String>()
            hashMap[GlobalVariables.PARAM.firstname] = et_firstName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.lastname] = et_lastName.text.toString().trim()
            hashMap[GlobalVariables.PARAM.email] = emailEdittext.text.toString().trim()
            hashMap[GlobalVariables.PARAM.mobile] = et_mobileNo.text.toString().trim()
            hashMap[GlobalVariables.PARAM.vehicleType] = mVehicleType
            hashMap[GlobalVariables.PARAM.vehicleMake] = et_vehiclemake.text.toString().trim()
            hashMap[GlobalVariables.PARAM.vehicleModel] = et_vehiclemodel.text.toString().trim()
            hashMap[GlobalVariables.PARAM.city] = et_city.text.toString().trim()
            hashMap[GlobalVariables.PARAM.state] = et_state.text.toString().trim()
            hashMap[GlobalVariables.PARAM.country] = mCountryName
            hashMap[GlobalVariables.PARAM.password] = passwordEdittext.text.toString().trim()

            checkEmailAndMobileExistAPI()
        }

    }

    val viewModel: DriverViewModel by viewModels()
    lateinit var hashMap:HashMap<String,RequestBody>

    private fun checkEmailAndMobileExistAPI() {
        hashMap = HashMap()
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
                    val mResponse: CheckEmailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                            val intent = Intent(this, UploadDocActivity::class.java)
                        intent.putExtra("fName", et_firstName.text.toString())
                        intent.putExtra("lName", et_lastName.text.toString())
                        intent.putExtra("eId", emailEdittext.text.toString())
                        intent.putExtra("mNumber", et_mobileNo.text.toString())
                        intent.putExtra("vType", mVehicleType)
                        intent.putExtra("make", et_vehiclemake.text.toString())
                        intent.putExtra("model", et_vehiclemodel.text.toString())
                        intent.putExtra("city", et_city.text.toString())
                        intent.putExtra("state", et_state.text.toString())
                        intent.putExtra("country", mCountryName)
                        intent.putExtra("pass", passwordEdittext.text.toString())
                        intent.putExtra("profileImage", firstimage)
                        startActivity(intent)
                         } }
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
