package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.advertiser.model.BuisnessDetailResponse
import com.stalkstock.advertiser.viewModel.AdvertiserViewModel
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.others.GlobalVariables
import kotlinx.android.synthetic.main.activity_business_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class BusinessProfileActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable>{
    val mContext: Context =this

    val viewModel: AdvertiserViewModel by lazy {
        ViewModelProvider(this).get(AdvertiserViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_business_profile)
        tv_heading.text = "Business Profile"

        iv_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
    }

    private fun getBusinessDetailApi() {
        val map = HashMap<String, String>()

        viewModel.getBusinessDetail(this, true,map)
        viewModel.mResponse.observe(this, this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.iv_back->{
                finish()
            }
            R.id.btn_edit->{
                startActivity(Intent(mContext, EditBusinessProfileActivity::class.java))
            }
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is BuisnessDetailResponse) {
                    val data = it.data

                    if (data.code==200){

                        businessnamee.text = data.body.advertiserDetail.firstName
                        businessnamee1.text = data.body.advertiserDetail.buisnessName
                        businessaboutdetails.text = data.body.advertiserDetail.buisnessDescription
                        Glide.with(this).load(data.body.advertiserDetail.image).into(imageset)
                        businesstypes.text = data.body.advertiserDetail.buisnessTypeName
                        business_license.text = data.body.advertiserDetail.buisnessLicense
                        businessemailid.text = data.body.email
                        businessmobile.text = data.body.mobile
                        businesstelephonenumber.text = data.body.advertiserDetail.buisnessPhone
                        businesswebsitename.text = data.body.advertiserDetail.website
                        tvBusinessAddress.text = data.body.advertiserDetail.buisnessAddress
                        tvaddressLine2.text = data.body.advertiserDetail.addressLine2
                        tvCity.text = data.body.advertiserDetail.city
                        tvState.text = data.body.advertiserDetail.state
                        tvZipCode.text = data.body.advertiserDetail.postalCode
                        tvCountry.text = data.body.advertiserDetail.country
                        Glide.with(this).load(data.body.advertiserDetail.image).into(imageset)


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

    override fun onResume() {
        super.onResume()
        getBusinessDetailApi()
    }
}