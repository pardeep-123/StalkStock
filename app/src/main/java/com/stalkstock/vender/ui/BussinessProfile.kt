package com.stalkstock.vender.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mender.utlis.interfaces.OnNoInternetConnectionListener
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.VendorBusinessDetailResponse
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_bussiness_profile.*

class BussinessProfile : AppCompatActivity(), Observer<RestObservable> {

    val viewModel: VendorViewModel by viewModels()
    var mData: VendorBusinessDetailResponse.Body? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bussiness_profile)
        val button = findViewById<Button>(R.id.businessprofilebutton)
        val imageView = findViewById<ImageView>(R.id.businessbackarrow)
        button.setOnClickListener {
            if (mData != null) {
                startActivity(
                    Intent(
                        this@BussinessProfile,
                        EditBussinessProfile::class.java
                    ).putExtra("data", mData)
                )
            }
            else {
                AppUtils.showNoInternetAlert(this,
                    getString(R.string.no_internet_connection),
                    object : OnNoInternetConnectionListener {
                        override fun onRetryApi() {
                            getBusinessDetail()
                        }
                    })
            }
        }
        imageView.setOnClickListener { onBackPressed() }
    }

    override fun onResume() {
        super.onResume()
        getBusinessDetail()
    }

    private fun getBusinessDetail() {
        viewModel.getVendorBusinessDetailApi(this, true)
        viewModel.mResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is VendorBusinessDetailResponse) {
                    val mResponse: VendorBusinessDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        mData = mResponse.body
                        val vendorDetail = mResponse.body.vendorDetail
                        imageset.loadImage(vendorDetail.shopLogo)
                        businessnamee.setText(vendorDetail.firstName)
                        businessnamee1.setText(vendorDetail.lastName)
                        businessaboutdetails.setText(vendorDetail.shopDescription)
                        business_license.setText(vendorDetail.buisnessLicense)
                        businessemailid.setText(mResponse.body.email)
                        businesstypes.setText(vendorDetail.buisnessTypeName)
                        var array =
                            this.resources.getStringArray(R.array.Select_business_delivery_type)
                        val get = array.get(vendorDetail.deliveryType + 1)
                        txtbusinessDeliverytypes.setText(get)
                        businessmobile.setText(mResponse.body.email)
                        businesstelephonenumber.setText(vendorDetail.buisnessPhone)
                        businesswebsitename.setText(vendorDetail.website)
                        tvAddressBusiness.setText(vendorDetail.shopAddress)
                        businessAddressLine2.setText(vendorDetail.addressLine2)
                        businessCity.setText(vendorDetail.city)
                        tvStateBusiness.setText(vendorDetail.state)
                        tvPostalcodeBusiness.setText(vendorDetail.postalCode)
                        tvCountryBusiness.setText(vendorDetail.country)
                    } else {
                        AppUtils.showErrorAlert(
                            this,
                            mResponse.message
                        )
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
//

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

}