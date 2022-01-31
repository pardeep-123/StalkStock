package com.stalkstock.commercial.view.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.activities.EditBusinessProfileActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.GetCommercialBuisnessDetail
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.business_profile.*
import kotlinx.android.synthetic.main.business_profile.btn_edit
import kotlinx.android.synthetic.main.business_profile.business_license
import kotlinx.android.synthetic.main.business_profile.businessaboutdetails
import kotlinx.android.synthetic.main.business_profile.businessemailid
import kotlinx.android.synthetic.main.business_profile.businessmobile
import kotlinx.android.synthetic.main.business_profile.businessnamee
import kotlinx.android.synthetic.main.business_profile.businessnamee1
import kotlinx.android.synthetic.main.business_profile.businesstelephonenumber
import kotlinx.android.synthetic.main.business_profile.businesstypes
import kotlinx.android.synthetic.main.business_profile.businesswebsitename
import java.util.HashMap

class BusinessProfile : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {

    val mContext: Context =this

    val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.business_profile)

        llBusinessType.visibility=View.GONE

        ivBusiness.setOnClickListener(this)
        btn_edit.setOnClickListener(this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is GetCommercialBuisnessDetail) {
                    val data = it.data

                    if (data.code==200){

                        businessnamee.text = data.body.commercialDetail.firstName+" "+data.body.commercialDetail.lastName
                        businessnamee1.text = data.body.commercialDetail.buisnessName
                        businessaboutdetails.text = data.body.commercialDetail.buisnessDescription
                        if(data.body.commercialDetail.buisnessLogo.isNullOrEmpty()){
                            imageset.setImageResource(R.drawable.camera_green)
                        }else{
                            Glide.with(this).load(data.body.commercialDetail.buisnessLogo).placeholder(R.drawable.camera_green).into(imageset)

                        }
                        businesstypes.text = data.body.commercialDetail.buisnessTypeName
                        business_license.text = data.body.commercialDetail.buisnessLicense
                        businessemailid.text = data.body.email
                        businessmobile.text = data.body.mobile
                        businesstelephonenumber.text = data.body.commercialDetail.buisnessPhone
                        businesswebsitename.text = data.body.commercialDetail.website
                        tvBusinessAddr.text = data.body.commercialDetail.buisnessAddress
                        tvAddrLine2.text = data.body.commercialDetail.addressLine2
                        tvCityC.text = data.body.commercialDetail.city
                        tvStateC.text = data.body.commercialDetail.state
                        tvZipCodeC.text = data.body.commercialDetail.postalCode
                        tvCountryC.text = data.body.commercialDetail.country


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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBusiness->{
                finish()
            }
            R.id.btn_edit->{
                startActivity(Intent(mContext, EditBusinessProfileActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getBusinessDetailApi()
    }

    private fun getBusinessDetailApi() {
        val map = HashMap<String, String>()

        viewModel.getCommercialBusinessProfile(this, true,map)
        viewModel.homeResponse.observe(this, this)
    }
}
