package com.stalkstock.consumer.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.advertiser.activities.ManagePaymentsActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.MyorderProductAdapter
import com.stalkstock.consumer.model.OrderDetailResponse
import com.stalkstock.utils.extention.checkObjectNull
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_orderdeatils.*
import java.util.HashMap

class OrderdeatilsActivity : AppCompatActivity(), Observer<RestObservable> {

    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderdeatils)

        tvStatus.setOnClickListener({
            val intent = Intent(applicationContext, ManagePaymentsActivity::class.java)
            startActivity(intent)
        })
        arrowBack.setOnClickListener({ onBackPressed() })

        if (intent.hasExtra("orderId"))
            getOrderDetail(intent.getStringExtra("orderId")!!)
    }

    private fun getOrderDetail(orderId:String) {
        val hashMap = HashMap<String, String>()
        hashMap.put("orderId", orderId)
        viewModel.getOrderDetailAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderDetailResponse) {
                    val mResponse: OrderDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        img.loadImage(mResponse.body.orderVendor.shopLogo)
                        kfc.setText(mResponse.body.orderVendor.shopName)
                        if (mResponse.body.orderStatus == 0)
                            tvStatus.setText("Pending")
                        tvText.setText("Your order from "+mResponse.body.orderVendor.shopName+" is on the way")
                        mRecyclerView.adapter = MyorderProductAdapter(this,mResponse.body.orderItems)
                        tvItemTotal.setText("$"+mResponse.body.netAmount)
                        tvRestCharges.setText("$"+mResponse.body.shopCharges)
                        tvDeliveryFee.setText("$"+mResponse.body.shippingCharges)
                        tvTotalAmount.setText("$"+mResponse.body.total)
                        tvOrderNumber.setText(mResponse.body.orderNo)
                        tvDateTime.setText(
                            AppUtils.changeDateFormat(
                                mResponse.body.updatedAt,
                                GlobalVariables.DATEFORMAT.DateTimeFormat1,
                                GlobalVariables.DATEFORMAT.DateTimeFormat2
                            )
                        )
                        if (!checkObjectNull(mResponse.body.orderAddress)){
                        if (!checkStringNull(mResponse.body.orderAddress.geoLocation))
                        tvDeliveryLocation.setText(mResponse.body.orderAddress.geoLocation)
                        else {
                            tvDeliveryLocation.visibility = View.GONE
                            textDelivery.visibility = View.GONE
                        }
                        } else {
                            tvDeliveryLocation.visibility = View.GONE
                            textDelivery.visibility = View.GONE
                        }
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
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
}