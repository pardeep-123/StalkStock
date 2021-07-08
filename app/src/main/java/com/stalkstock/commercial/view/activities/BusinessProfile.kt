package com.live.stalkstockcommercial.ui.view.fragments.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.advertiser.activities.EditBusinessProfileActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.model.ModelAddToCart
import com.stalkstock.consumer.model.ModelCartData
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import com.stalkstock.viewmodel.HomeViewModel
import com.tamam.utils.others.AppUtils
import kotlinx.android.synthetic.main.business_profile.*

class BusinessProfile : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.business_profile)

        ivBusiness.setOnClickListener { onBackPressed() }
        btn_edit.setOnClickListener { startActivity(Intent(this,EditBusinessProfileActivity::class.java)) }
    }


}
