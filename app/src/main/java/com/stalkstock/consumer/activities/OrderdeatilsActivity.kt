package com.stalkstock.consumer.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.R
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
import kotlinx.android.synthetic.main.row_myorder.view.*
import java.util.HashMap

class OrderdeatilsActivity : AppCompatActivity(), Observer<RestObservable> {

    val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orderdeatils)


        arrowBack.setOnClickListener { onBackPressed() }

        if (intent.hasExtra("orderId"))
            getOrderDetail(intent.getStringExtra("orderId")!!)
    }

    private fun getOrderDetail(orderId: String) {
        val hashMap = HashMap<String, String>()
        hashMap["orderId"] = orderId
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
                        kfc.text = mResponse.body.orderVendor.shopName
                        tvLocation.text=mResponse.body.orderVendor.ShopAddress

                        setOrderStatus(mResponse.body.orderStatus)


                        tvText.text = "Your order from " + mResponse.body.orderVendor.shopName + " is on the way"
                        mRecyclerView.adapter = MyorderProductAdapter(this, mResponse.body.orderItems)
                        tvItemTotal.text = "$" + mResponse.body.netAmount
                        tvRestCharges.text = "$" + mResponse.body.shopCharges
                        tvDeliveryFee.text = "$" + mResponse.body.shippingCharges
                        tvTotalAmount.text = "$" + mResponse.body.total
                        tvOrderNumber.text = mResponse.body.orderNo
                        tvDateTime.text = AppUtils.changeDateFormat(
                            mResponse.body.updatedAt,
                            GlobalVariables.DATEFORMAT.DateTimeFormat1,
                            GlobalVariables.DATEFORMAT.DateTimeFormat2
                        )
                        if (!checkObjectNull(mResponse.body.orderAddress)) {
                            if (!checkStringNull(mResponse.body.orderAddress.geoLocation))
                                tvDeliveryLocation.text = mResponse.body.orderAddress.geoLocation
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

    private fun setOrderStatus(orderStatus: Int) {
        when(orderStatus){
            0->{ tvStatus.text = "Pending" }
            1->{tvStatus.text = "In Progress"}
            2->{tvStatus.text = "Packed"}
            4->{tvStatus.text= "Completed"}
            5->{tvStatus.text = "Cancelled"}
            6->{tvStatus.text= "Rejected"}
            else->{tvStatus.text= "Error"}
        }

    }
}