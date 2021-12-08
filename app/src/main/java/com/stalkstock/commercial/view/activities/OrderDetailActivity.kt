package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.MyorderProductAdapter
import com.stalkstock.consumer.model.OrderDetailResponse
import com.stalkstock.rating.RatingActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_orderdeatils.*
import kotlinx.android.synthetic.main.activity_parent.*
import kotlinx.android.synthetic.main.activity_parent.img
import kotlinx.android.synthetic.main.activity_parent.kfc
import kotlinx.android.synthetic.main.activity_parent.tvStatus
import java.util.*

class OrderDetailActivity : AppCompatActivity(), Observer<RestObservable> {
    val viewModel: HomeViewModel by viewModels()
    lateinit var currency: String
    private lateinit var publicData: OrderDetailResponse
    val list= ArrayList<OrderDetailResponse.Body.OrderItem>()

    var orderId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        currency = "$"
        btnRating.setOnClickListener {
            val intent = Intent(this, RatingActivity::class.java)
            if (publicData != null)
                intent.putExtra("body", publicData)
            startActivity(intent)
        }

        ivBackDetail.setOnClickListener { onBackPressed() }


        if (intent.hasExtra("orderId"))
            orderId = intent.getStringExtra("orderId").toString()
        getOrderDetailApi(orderId)


        when (intent.extras!!.getString("fragment")) {
            "0" -> {
                tv_delivered_by.visibility = View.GONE
                tv_delivered_by_value.visibility = View.GONE
                tv_delivered_to.visibility = View.VISIBLE
                tv_delivered_to_value.visibility = View.VISIBLE
                tv_delivered_to.text = "DELIVERED BY"
            }
            "1" -> {
                tvStatus.setTextColor(resources.getColor(R.color.themeColor))
            }
        }
        ivBackDetail.setOnClickListener { onBackPressed() }
    }

    private fun getOrderDetailApi(orderId: String) {
        val hashMap = HashMap<String, String>()
        hashMap["orderId"] = orderId
        viewModel.getOrderDetailAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onResume() {
        super.onResume()
        getOrderDetailApi(orderId)
        if (MyApplication.instance.getString("usertype").equals("4")) {

            tv_rest_charges.visibility = View.GONE
            relDelveryFee.visibility = View.GONE
            rl_visa.visibility = View.VISIBLE
            tvTextCh.text = "Shop Charges"
        } else {
            rl_visa.visibility = View.GONE
        }
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderDetailResponse) {
                    val mResponse: OrderDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        publicData = mResponse
                        for(i in 0 until publicData.body.orderItems.size){
                            if(publicData.body.orderItems[i].isReview==0){

                                list.add(publicData.body.orderItems[i])
                            }
                        }
                        if(publicData.body.orderStatus<4){
                            btnRating.visibility=View.GONE
                        }
                        else if(publicData.body.orderStatus==1 && publicData.body.orderStatus==4 && publicData.body.isDriverReview==1 && publicData.body.isVendorReview==1
                                 && publicData.body.isUserReview==0 && list.size==0 ){
                                 btnSubmitRating.visibility=View.GONE
                            btnRating.visibility=View.GONE
                        }else{
                             btnRating.visibility=View.VISIBLE
                        }

                        img.loadImage(mResponse.body.orderVendor.shopLogo)
                        kfc.text = mResponse.body.orderVendor.shopName
                        setOrderStatus(mResponse.body.orderStatus)
                        tvTexts.text = "Your order from " + mResponse.body.orderVendor.shopName + " is on the way"
                        mRecyclerViews.adapter = MyorderProductAdapter(this, mResponse.body.orderItems)
                        tvItemTotals.text = currency + mResponse.body.netAmount
                        tvShopCharges.text = currency + mResponse.body.shopCharges
                        tvDeliveryCharges.text = currency + mResponse.body.shippingCharges
                        tv_total.text = currency + mResponse.body.total
                        tvAdress.text = mResponse.body.orderVendor.ShopAddress
                        tvOrderNumbers.text = mResponse.body.orderNo
                        tv_delivered_to_value.text =
                            mResponse.body.orderAddress.street_address + " " + mResponse.body.orderAddress.geoLocation +
                                    "," + mResponse.body.orderAddress.city + "," + mResponse.body.orderAddress.state + "," + mResponse.body.orderAddress.zipcode

                        tvDateTimes.text = AppUtils.changeDateFormat(
                            mResponse.body.updatedAt,
                            GlobalVariables.DATEFORMAT.DateTimeFormat1,
                            GlobalVariables.DATEFORMAT.DateTimeFormat2
                        )

                        if (mResponse.body.paymentMethod == 1) {
                            ivCard.visibility = View.VISIBLE
                            ivPay.visibility = View.GONE
                        } else {
                            ivCard.visibility = View.GONE
                            ivPay.visibility = View.VISIBLE
                        }
                        /*     if (!checkObjectNull(mResponse.body.orderAddress)){
                                 if (!checkStringNull(mResponse.body.orderAddress.geoLocation))
                                     tvDeliveryLocation.text = mResponse.body.orderAddress.geoLocation
                                 else {
                                     tvDeliveryLocation.visibility = View.GONE
                                     textDelivery.visibility = View.GONE
                                 }
                             } else {
                                 tvDeliveryLocation.visibility = View.GONE
                                 textDelivery.visibility = View.GONE
                             }*/
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
            0->{ tvStatus.text = "Pending"
                tvStatus.setTextColor(resources.getColor(R.color.orange_colour))}
            1->{tvStatus.text = "In Progress"
                tvStatus.setTextColor(resources.getColor(R.color.orange_colour))}
            2->{tvStatus.text = "Packed"
                tvStatus.setTextColor(resources.getColor(R.color.orange_colour))}
            3->{tvStatus.text = "On the way"
                tvStatus.setTextColor(resources.getColor(R.color.orange_colour))}
            4->{
                tvStatus.text= "Completed"
                tvStatus.setTextColor(resources.getColor(R.color.green_colour))
            }
            5->{tvStatus.text = "Cancelled"
                tvStatus.setTextColor(resources.getColor(R.color.red_dark_colour))}
            6->{tvStatus.text= "Rejected"
                tvStatus.setTextColor(resources.getColor(R.color.red_dark_colour))}
            else->{tvStatus.text= "Error"
                tvStatus.setTextColor(resources.getColor(R.color.red_dark_colour))}
        }

    }

    override fun onRestart() {
        super.onRestart()
        getOrderDetailApi(orderId)
    }


}