package com.stalkstock.driver

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.activities.PreviewActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.driver.models.DriverDocResponse
import com.stalkstock.driver.models.DriverSignUpResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.extention.checkStringNull
import kotlinx.android.synthetic.main.activity_driver_information.*
import kotlinx.android.synthetic.main.toolbar.*
import okhttp3.RequestBody

class DriverInformationActivity : AppCompatActivity(), Observer<RestObservable> {
    private var publicData: DriverDocResponse?=null
    val mContext: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        setContentView(R.layout.activity_driver_information)
        tv_heading.text = "Driver Information"
        iv_back.setOnClickListener {
            finish()
        }
        btn_editInfo.setOnClickListener {
            val intent = Intent(mContext, EditDriverInfoActivity::class.java)
            if (publicData != null)
                intent.putExtra("body", publicData)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getDocumentDetailAPI()
    }

    val viewModel: DriverViewModel by viewModels()

    private fun getDocumentDetailAPI() {
        val hashMap = HashMap<String, String>()
        viewModel.getDocumentDetail(this, true, hashMap)
        viewModel.mResponse.observe(this, this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is DriverDocResponse) {
                    val data = it.data as DriverDocResponse
                    if (data.code == 200) {
                        setdata(data)
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

    private fun setdata(data: DriverDocResponse) {
        publicData = data
        Glide.with(this).load(data.body.driverDetail.licenceFrontImage).into(imgFrontDriver)
        Glide.with(this).load(data.body.driverDetail.licenceBackImage).into(imgBackDriver)
        Glide.with(this).load(data.body.driverDetail.registrationImage).into(imgDriverRegistration)
        Glide.with(this).load(data.body.driverDetail.insuranceProof).into(imgDriverProofInsurance)
        txtVehicleName.setText(data.body.driverDetail.vehicleType)
        if (!checkStringNull(data.body.driverDetail.vehicleMake))
            txtMake.setText(data.body.driverDetail.vehicleMake)
        txtModel.setText(data.body.driverDetail.vehicleModel)
        if (!checkStringNull(data.body.driverDetail.licenceNumber))
            txtDriverLicenceNumber.setText(data.body.driverDetail.licenceNumber)
        if (!checkStringNull(data.body.driverDetail.licenceExpiryDate))
            txtDriverLicenceValidThrough.setText(data.body.driverDetail.licenceExpiryDate)
        if (!checkStringNull(data.body.driverDetail.registrationNumber))
            txtDriverRegNumber.setText(data.body.driverDetail.registrationNumber)
        if (!checkStringNull(data.body.driverDetail.registrationExpiryDate))
            txtDriverRegistrationValidThrough.setText(data.body.driverDetail.registrationExpiryDate)
        if (!checkStringNull(data.body.driverDetail.insuranceCompany))
            txtInsuranceCompany.setText(data.body.driverDetail.insuranceCompany)
        if (!checkStringNull(data.body.driverDetail.insurancePolicy))
            txtInsurancePolicy.setText(data.body.driverDetail.insurancePolicy)
        if (!checkStringNull(data.body.driverDetail.insuranceExpiryDate))
            txtInsuranceValid.setText(data.body.driverDetail.insuranceExpiryDate)
        if (!checkStringNull(data.body.driverDetail.address))
            txtDriverAddress.setText(data.body.driverDetail.address)
        if (!checkStringNull(data.body.driverDetail.addressLine2))
            txtDriverAddressLine2.setText(data.body.driverDetail.addressLine2)
        if (!checkStringNull(data.body.driverDetail.city))
            txtDriverCity.setText(data.body.driverDetail.city)
        if (!checkStringNull(data.body.driverDetail.state))
            txtDriverState.setText(data.body.driverDetail.state)
        if (!checkStringNull(data.body.driverDetail.postalCode))
            txtDriverZipCode.setText(data.body.driverDetail.postalCode)
        if (!checkStringNull(data.body.driverDetail.country))
            txtDriverCountry.setText(data.body.driverDetail.country)
    }
}