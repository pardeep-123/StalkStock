package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.ModelProductDetail
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.vender.adapter.CustomPagerAdapter
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.RequestBody
import java.util.HashMap

class ProductDetail : BaseActivity(), Observer<RestObservable> {

    var customPagerAdapter:CustomPagerAdapter?=null
    override fun getContentId(): Int {
        return R.layout.activity_product_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val backarrow = findViewById<ImageView>(R.id.product_backarrow)
        currentProductID = intent.getStringExtra("product_id")!!
        val btn = findViewById<Button>(R.id.edit_btn)
        val btn2 = findViewById<Button>(R.id.delete_btn)
        btn2.setOnClickListener {

            deleteProductDialog(currentProductID)
           // deleteProduct(currentProductID)
        }
        btn.setOnClickListener {
            val intent = Intent(this@ProductDetail, EditProduct::class.java)
            intent.putExtra("body",currentProductModel)
            startActivity(intent)
        }
        backarrow.setOnClickListener { onBackPressed() }


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
                        deleteSuccessDialog()

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

    fun deleteProductDialog(currentProductID: String) {
        val logoutUpdatedDialogs = Dialog(this)
        logoutUpdatedDialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialogs.setContentView(R.layout.delete_successfully_alert)
        logoutUpdatedDialogs.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialogs.setCancelable(true)
        logoutUpdatedDialogs.setCanceledOnTouchOutside(false)
        logoutUpdatedDialogs.window!!.setGravity(Gravity.CENTER)
        logoutUpdatedDialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btncontinue = logoutUpdatedDialogs.findViewById<Button>(R.id.btn_yes)
        val upgrade_cancel = logoutUpdatedDialogs.findViewById<Button>(R.id.btn_no)
        btncontinue.setOnClickListener {
            logoutUpdatedDialogs.dismiss()
            deleteProduct(currentProductID)
        }
        upgrade_cancel.setOnClickListener {
            logoutUpdatedDialogs.dismiss()
        }
        logoutUpdatedDialogs.show()
    }

    fun deleteSuccessDialog() {
        val logoutUpdatedDialogs = Dialog(this)
        logoutUpdatedDialogs.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logoutUpdatedDialogs.setContentView(R.layout.dialog_delete_product)
        logoutUpdatedDialogs.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        logoutUpdatedDialogs.setCancelable(true)
        logoutUpdatedDialogs.setCanceledOnTouchOutside(false)
        logoutUpdatedDialogs.window!!.setGravity(Gravity.CENTER)
        logoutUpdatedDialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btncontinue = logoutUpdatedDialogs.findViewById<Button>(R.id.btn_yes)

        btncontinue.setOnClickListener {
            logoutUpdatedDialogs.dismiss()
            Handler(Looper.getMainLooper()).postDelayed({
                         finish()
                         //Do something after 100ms
                     }, 1000)

        }

        logoutUpdatedDialogs.show()
    }

    private fun setData(mResponse: ModelProductDetail) {
        currentProductModel = mResponse
        var imagesList= mResponse.body.productImage
        tvProductName.text= mResponse.body.name
        product_Bacongrill.text = mResponse.body.brandName
        businessnamee1.text = mResponse.body.productTag[0].tag
        businessmobile.text = mResponse.body.country
        businesstelephonenumber.text = mResponse.body.productMeasurement.name
        businesswebsitename.text = "$" + mResponse.body.mrp.toString()
        businesscity.text = mResponse.body.description

        if (mResponse.body.availability == 1) {
            businesstypes.text = "Available"
        } else
            businesstypes.text = "Not Available"

        customPagerAdapter = CustomPagerAdapter(this,
            imagesList as MutableList<ModelProductDetail.Body.ProductImage>
        )
        viewPagerImages.adapter = customPagerAdapter
        viewPagerImages.autoScroll(4000)
        if (imagesList.size > 1) {
            tab_layout.visibility = View.VISIBLE
        } else {
            tab_layout.visibility = View.GONE
        }
        tab_layout.setupWithViewPager(viewPagerImages, true)
    }
    private fun ViewPager.autoScroll(interval: Long) {

        val handler = Handler(Looper.getMainLooper())
        var scrollPosition = 0

        val runnable = object : Runnable {

            override fun run() {
                val count = customPagerAdapter?.count!!
                setCurrentItem(scrollPosition++ % count, true)

                handler.postDelayed(this, interval)
            }
        }

        addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                // Updating "scroll position" when user scrolls manually
                scrollPosition = position + 1
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not necessary
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Not necessary
            }
        })

        handler.post(runnable)
    }
}