package com.stalkstock.rating

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.model.OrderDetailResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.CommonResponseModel
import kotlinx.android.synthetic.main.activity_main2.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class RatingActivity:AppCompatActivity(), RatingProductAdapter.RatingProductInterface,
    Observer<RestObservable>, View.OnClickListener {
    var orderId=""
    var ratingProductAdapter:RatingProductAdapter?=null
    val viewModel: DriverViewModel by viewModels()
    var isDriverReview=0
    var isVendorReview=0
    var isProductReview=0
    var type=""
    val list= ArrayList<OrderDetailResponse.Body.OrderItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        getIntentData()
        setOnClicks()
    }

    private fun setOnClicks() {
        btnDriverRating.setOnClickListener(this)
        btnVendorRating.setOnClickListener(this)
    }

    private fun getIntentData() {
        if (intent.hasExtra("body")) {
            val orderDetail = intent.getSerializableExtra("body") as OrderDetailResponse
            orderId= orderDetail.body.id.toString()
            setData(orderDetail)
        }
    }
    fun setRatingData(isDriverReview:Int,isVendorReview:Int){
        if(isVendorReview==1 && isDriverReview==1 && list.size==0){
            onBackPressed()
        }
        if(isVendorReview==1) {
            tvVendorRating.visibility = View.GONE
            ratingVendor.visibility = View.GONE
            btnVendorRating.visibility = View.GONE

        }else{
            tvVendorRating.visibility = View.VISIBLE
            ratingVendor.visibility = View.VISIBLE
            btnVendorRating.visibility = View.VISIBLE
        }
        if(isDriverReview==1){

            tvDriverRating.visibility=View.GONE
            ratingDriver.visibility=View.GONE
            btnDriverRating.visibility=View.GONE

        }else{
            tvDriverRating.visibility=View.VISIBLE
            ratingDriver.visibility=View.VISIBLE
            btnDriverRating.visibility=View.VISIBLE
        }
    }

    private fun setData(data: OrderDetailResponse) {
        isDriverReview= data.body.isDriverReview
        isVendorReview= data.body.isVendorReview
        if(isVendorReview==1) {
            tvVendorRating.visibility = View.GONE
            ratingVendor.visibility = View.GONE
            btnVendorRating.visibility = View.GONE

        }else{
            tvVendorRating.visibility = View.VISIBLE
            ratingVendor.visibility = View.VISIBLE
            btnVendorRating.visibility = View.VISIBLE
        }
        if(isDriverReview==1){

            tvDriverRating.visibility=View.GONE
            ratingDriver.visibility=View.GONE
            btnDriverRating.visibility=View.GONE

        }else{
            tvDriverRating.visibility=View.VISIBLE
            ratingDriver.visibility=View.VISIBLE
            btnDriverRating.visibility=View.VISIBLE
        }

        for(i in 0 until data.body.orderItems.size){
            if(data.body.orderItems[i].isReview==0){
                list.add(data.body.orderItems[i])
            }
        }

        Log.e("listSize",list.size.toString())
        if(list.size>0){
            rvProductRating.visibility=View.VISIBLE
            ratingProductAdapter= RatingProductAdapter(this, list)
            ratingProductAdapter?.ratingProductInterface=this
            rvProductRating.adapter = ratingProductAdapter
        }else{
            rvProductRating.visibility=View.GONE
        }


    }

    override fun onSubmitRating(
        position: Int,
        rating: String,
        orderItemId: String,
        productId: String
    ) {  val hashMap = HashMap<String, Any>()
        list.removeAt(position)
        hashMap["orderId"] = orderId.toInt()
        hashMap["rating"] = rating.toFloat()
        hashMap["orderItemId"] = orderItemId.toInt()
        hashMap["productId"] = productId.toInt()
        isProductReview=1
        viewModel.addProductRating(this, true, hashMap)
        viewModel.mResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is CommonResponseModel) {
                    val mResponse: CommonResponseModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        if(isProductReview==1){
                            ratingProductAdapter?.notifyDataSetChanged()
                        }else{
                            setRatingData(isDriverReview,isVendorReview)
                        }

//                        finish()
//                        startActivity(intent)
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
            R.id.btnDriverRating ->{
                isDriverReview=1
                val hashMap = HashMap<String, Any>()
                hashMap["orderId"] = orderId.toInt()
                hashMap["rating"] = ratingVendor.rating
                viewModel.addDriverReview(this,true,hashMap)
                viewModel.mResponse.observe(this, this)
            }

            R.id.btnVendorRating->{
                isVendorReview=1
                val hashMap = HashMap<String, Any>()
                hashMap["orderId"] = orderId.toInt()
                hashMap["rating"] = ratingVendor.rating
                viewModel.addUserReview(this,true,hashMap)
                viewModel.mResponse.observe(this, this)
            }
        }
    }

}