package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.Chat
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.utils.others.Util
import com.stalkstock.vender.Model.BidData
import com.stalkstock.vender.Model.CommonResponseModel
import com.stalkstock.vender.Model.OrderItem
import com.stalkstock.vender.Model.VendorBidDetailResponse
import com.stalkstock.vender.adapter.BidOrderAdapter
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import okhttp3.RequestBody

class BidDetailNew: AppCompatActivity(), Observer<RestObservable>, View.OnClickListener {
    val mContext: Context = this
    val viewModel: VendorViewModel by viewModels()
    var bidId = ""
    var mUtil = Util()
    var arrayList = ArrayList<OrderItem>()
    var bidOrderAdapter: BidOrderAdapter? = null
    var userId = 0
    var chatId = ""
    var userName = ""
    var userImage = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bid_detail)

        getIntentData()

        setOnClicks()

    }

    private fun getIntentData() {
        bidId = intent.getStringExtra("bidId").toString()
        getBidDetail(bidId)

    }

    private fun getBidDetail(bidId: String) {
        val hashMap = HashMap<String, RequestBody>()
        hashMap["bidId"] = mUtil.createPartFromString(bidId)

        //   hashMap["type"] = "0"    // 0=>new request 1=>accepted
        viewModel.vendorBiddingDetail(this, true, hashMap)
        viewModel.mResponse.observe(this, this)
    }
    private fun setOnClicks() {
        placebid_button.setOnClickListener(this)
        newchat.setOnClickListener(this)
        biddetails_backarrow.setOnClickListener(this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is VendorBidDetailResponse) {
                    val mResponse: VendorBidDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setBidData(mResponse.body)

                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                } else if (it.data is CommonResponseModel) {
                    val mResponse: CommonResponseModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        Log.i("====", mResponse.message)
                        onBackPressed()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                    Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {

                    Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.newchat -> {
                val intent = Intent(this, Chat::class.java)
                intent.putExtra("screen_type", "bid")
                intent.putExtra("id", bidId.toString())
                intent.putExtra("userId", userId)
                intent.putExtra("chatId", chatId)
                intent.putExtra("userName", userName)
                intent.putExtra("userImage", userImage)
                intent.putExtra("paramName", "bidId")
                startActivity(intent)
            }

            R.id.biddetails_backarrow -> {
                onBackPressed()
            }

            R.id.placebid_button -> {
                editDialog()
            }
        }
    }
    private fun editDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.biddetailsalertbox)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)

        dialog.submitbutton.setOnClickListener {
            if (dialog.edtSellingPrice.text.toString().isEmpty()) {
                dialog.edtSellingPrice.error = resources.getString(R.string.please_enter_sale_price)
            } else if (dialog.edtSellngDesc.text.toString().isEmpty()) {
                dialog.edtSellngDesc.error = resources.getString(R.string.please_enter_sale_terms)
            } else {
                val hashMap = HashMap<String, RequestBody>()
                hashMap["bidId"] = mUtil.createPartFromString(bidId)
                hashMap["amount"] = mUtil.createPartFromString(dialog.edtSellingPrice?.text.toString())
                hashMap["description"] = mUtil.createPartFromString(dialog.edtSellngDesc?.text.toString())
                viewModel.vendorAcceptBid(this, true, hashMap)
                viewModel.mResponse.observe(this, this)

                bidamt.visibility = View.VISIBLE
                biddisc.visibility = View.VISIBLE
                placebid_button.tag = 1
                placebid_button.text = "Place Bid"
                if (placebid_button.text.toString() == "Place Bid") {
                    placebid_button.text = "Edit Bid"
                    it.tag = 1 //pause
                } else {
                    val status = it.tag as Int
                    if (status == 1) {
                        it.tag = 0 //pause
                    } else {
                        placebid_button.text = "Edit Bid"
                        it.tag = 1 //pause
                    }
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun setBidData(body: BidData) {
        userId = body.commercialDetail.id
        chatId = body.chatId.toString()
        requestid.text = "Request Id: " + body.requestNo
        bidid.text = "Bid : " + body.bidCount

        arrayList.clear()
        arrayList.addAll(body.orderItems)
        bidOrderAdapter = BidOrderAdapter(this, arrayList)
        rvOrders.layoutManager = LinearLayoutManager(this)
        rvOrders.adapter = bidOrderAdapter
//        tvCategory.text= body.orderItems[0].product.categoryName
//        tvProduct.text= body.orderItems[0].product.name
//        tvQty.text= body.orderItems[0].qty.toString()
//        tvMesurement.text= body.orderItems[0].product.measurementName
        biddate.text = AppUtils.changeDateFormat(
            body.createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat1,
            GlobalVariables.DATEFORMAT.DateTimeFormat2
        )
//        biddate.text=mUtil.toDate(body.createdAt,"MMM,dd,yyyy")
//        bidtime.text= mUtil.toDate(body.createdAt,"hh:mm")
        Glide.with(this).load(body.commercialDetail.image).into(bidimguser as CircleImageView)
        bidusername.text = body.commercialDetail.firstName + " " + body.commercialDetail.lastName
        userName = body.commercialDetail.firstName + " " + body.commercialDetail.lastName
        userImage = body.commercialDetail.image
    }
}