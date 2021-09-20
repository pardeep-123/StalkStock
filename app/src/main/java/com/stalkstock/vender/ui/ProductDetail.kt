package com.stalkstock.vender.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelProductDetail
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_product_detail.*
import okhttp3.RequestBody
import java.util.HashMap

class ProductDetail : BaseActivity(), Observer<RestObservable> {
    override fun getContentId(): Int {
        return R.layout.activity_product_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backarrow = findViewById<ImageView>(R.id.product_backarrow)
        val btn = findViewById<Button>(R.id.edit_btn)
        val btn2 = findViewById<Button>(R.id.delete_btn)
        btn2.setOnClickListener { deleteProduct(currentProductID) }
        btn.setOnClickListener {
            val intent = Intent(this@ProductDetail, EditProduct::class.java)
            intent.putExtra("body",currentProductModel)
            startActivity(intent)
        }
        backarrow.setOnClickListener { onBackPressed() }

        currentProductID = intent.getStringExtra("product_id")!!
    }

    private fun deleteProduct(currentProductID: String) {
        viewModel.deleteVendorProductAPI(this, true, currentProductID.toInt())
        viewModel.homeResponse.observe(this, this)
    }

    private lateinit var currentProductModel: ModelProductDetail
    private var currentProductID = ""
    val viewModel: HomeViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        getproductDetailAPI()
    }

    private fun getproductDetailAPI() {
        val map = HashMap<String, RequestBody>()
        map.put("product_id", mUtils.createPartFromString(currentProductID))
        viewModel.getVendorProductDetailsAPI(this, true, map)
        viewModel.homeResponse.observe(this, this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is ModelProductDetail) {
                    val mResponse: ModelProductDetail = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        setData(mResponse)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                            //Do something after 100ms
                        }, 2000)
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message.toString())
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

    private fun setData(mResponse: ModelProductDetail) {
        currentProductModel = mResponse
        adduploadimages.loadImage(mResponse.body.productImage[0].image)
        product_Bacongrill.setText(mResponse.body.brandName)
        businessnamee1.setText(mResponse.body.productTag[0].tag)
        businessmobile.setText(mResponse.body.country)
        businesstelephonenumber.setText(mResponse.body.productMeasurement.name)
        businesswebsitename.setText("$" + mResponse.body.mrp.toString())
        businesscity.setText(mResponse.body.description)

        if (mResponse.body.availability == 1) {
            businesstypes.setText("Available")
        } else
            businesstypes.setText("Not Available")
    }
}