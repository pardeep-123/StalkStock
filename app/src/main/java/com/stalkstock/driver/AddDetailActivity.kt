package com.stalkstock.driver

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.advertiser.activities.LoginActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.DriverSignUpResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.savePrefrence
import kotlinx.android.synthetic.main.activity_add_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.verification_popup.*
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddDetailActivity : BaseActivity(), Observer<RestObservable> {

    private val myCalendar = Calendar.getInstance()
    private lateinit var date: DatePickerDialog.OnDateSetListener
    val viewModel: DriverViewModel by viewModels()
    var mProfileImage = ""
    var mLicenseimage1 = ""
    var mLicenseimage2 = ""
    var mRegistrationImage = ""
    var mInsuranceImage = ""
    var fName: String = ""
    var lName : String= ""
    var eId : String= ""
    var mNumber: String = ""
    var vType: String = ""
    var model : String= ""
    var make: String = ""
    var city: String = ""
    var state: String = ""
    var pass : String= ""
    var country: String = ""
    var type=1

    override fun getContentId(): Int {
        return R.layout.activity_add_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateLabel()
        }
        tv_heading.text = "Add Details"
        edText1.setOnClickListener {
            type=1
            datePicker()
        }
        edText3.setOnClickListener {
            type=2
            datePicker()
        }


        iv_back.setOnClickListener {finish()}
        btn_Continue.setOnClickListener {
            signUpApi()
        }

        mProfileImage = intent.getStringExtra("profileImage")!!
        mLicenseimage1 = intent.getStringExtra("license1")!!
        mLicenseimage2 = intent.getStringExtra("license2")!!
        mRegistrationImage = intent.getStringExtra("registration")!!
        mInsuranceImage = intent.getStringExtra("insurance")!!
        fName = intent.getStringExtra("fName").toString()
        lName = intent.getStringExtra("lName").toString()
        eId = intent.getStringExtra("eId").toString()
        mNumber = intent.getStringExtra("mNumber").toString()
        vType = intent.getStringExtra("vType").toString()
        make = intent.getStringExtra("make").toString()
        model = intent.getStringExtra("model").toString()
        city = intent.getStringExtra("city").toString()
        state = intent.getStringExtra("state").toString()
        country = intent.getStringExtra("country").toString()
        pass = intent.getStringExtra("pass").toString()
    }

    private fun dialogconfirmation() {
        val  dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.verification_popup)

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.setGravity(Gravity.CENTER)

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.btn_ok11.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            dialog.dismiss()
            finishAffinity()
        }
        dialog.show()
    }

    private fun signUpApi()
    {
        when {
            checkStringNull(edText.text.toString()) -> {
                edText.requestFocus()
                edText.error = resources.getString(R.string.please_enter_driver_license_number)
            }
            checkStringNull(edText1.text.toString()) -> {
                edText1.requestFocus()
                edText1.error = resources.getString(R.string.please_enter_driver_license_exp_date)
            }
            checkStringNull(edText2.text.toString()) -> {
                edText2.requestFocus()
                edText2.error = resources.getString(R.string.please_enter_registration_number)
            }
            checkStringNull(edText3.text.toString()) -> {
                edText3.requestFocus()
                edText3.error = resources.getString(R.string.please_enter_registration_expdate)
            }
            else -> {
                val hashMap = HashMap<String, RequestBody>()
                hashMap[GlobalVariables.PARAM.firstname] = mUtils.createPartFromString(fName)
                hashMap[GlobalVariables.PARAM.lastname] = mUtils.createPartFromString(lName)
                hashMap[GlobalVariables.PARAM.email] = mUtils.createPartFromString(eId)
                hashMap[GlobalVariables.PARAM.mobile] = mUtils.createPartFromString(mNumber)
                hashMap[GlobalVariables.PARAM.vehicleType] = mUtils.createPartFromString(vType)
                hashMap[GlobalVariables.PARAM.vehicleMake] = mUtils.createPartFromString(make)
                hashMap[GlobalVariables.PARAM.vehicleModel] = mUtils.createPartFromString(model)
                hashMap[GlobalVariables.PARAM.city] = mUtils.createPartFromString(city)
                hashMap[GlobalVariables.PARAM.state] = mUtils.createPartFromString(state)
                hashMap[GlobalVariables.PARAM.country] = mUtils.createPartFromString(country)
                hashMap[GlobalVariables.PARAM.password] = mUtils.createPartFromString(pass)
                hashMap[GlobalVariables.PARAM.licenceNumber] = mUtils.createPartFromString(edText.text.toString())
                hashMap[GlobalVariables.PARAM.registrationNumber] = mUtils.createPartFromString(edText2.text.toString())
                hashMap[GlobalVariables.PARAM.licenceExpiryDate] = mUtils.createPartFromString(edText1.text.toString())
                hashMap[GlobalVariables.PARAM.registrationExpiryDate] = mUtils.createPartFromString(edText3.text.toString())
                viewModel.driverSignUpApi(this, true, hashMap,mProfileImage,mLicenseimage1,mLicenseimage2,mRegistrationImage,mInsuranceImage,mUtils)
                viewModel.mResponse.observe(this, this)
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is DriverSignUpResponse) {
                    val data = it.data as DriverSignUpResponse
                   if (data.code == 200) {
                       dialogconfirmation()
                       setData(data)
                       //Verification is in progress. Please wait for admin approval.
                       startActivity(Intent(this, LoginActivity::class.java))
                       finishAffinity()
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

    private fun setData(mResponse: DriverSignUpResponse) {
        savePrefrence(GlobalVariables.SHARED_PREF.AUTH_KEY, mResponse.body.token)
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

    private fun datePicker() {

        val datePickerDialog = DatePickerDialog(this, date,  myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH])

        datePickerDialog.datePicker.minDate= System.currentTimeMillis() -1000
        datePickerDialog.show()

    }

    private fun updateDateLabel() {
        val dateFormat = "dd/MM/yyyy" //In which you need put here
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        if(type==1){
            edText1.setText(sdf.format(myCalendar.time))
        }else{
            edText3.setText(sdf.format(myCalendar.time))
        }


    }

}
