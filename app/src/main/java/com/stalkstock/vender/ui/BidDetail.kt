package com.stalkstock.vender.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
import com.stalkstock.vender.adapter.AccpetAdapter
import com.stalkstock.vender.adapter.BidOrderAdapter
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_bid_detail.*
import kotlinx.android.synthetic.main.activity_bid_detail.biddate
import kotlinx.android.synthetic.main.activity_bid_detail.bidid
import kotlinx.android.synthetic.main.activity_bid_detail.bidimguser
import kotlinx.android.synthetic.main.activity_bid_detail.bidtime
import kotlinx.android.synthetic.main.activity_bid_detail.bidusername
import kotlinx.android.synthetic.main.activity_bid_detail.requestid
import kotlinx.android.synthetic.main.activity_bid_product.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_order_details.*
import kotlinx.android.synthetic.main.biddetailsalertbox.*
import kotlinx.android.synthetic.main.bidprdouctaccpetlist.*
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class BidDetail : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {

    val viewModel: VendorViewModel by viewModels()
    var bidId = ""
    var mUtil = Util()
    var arrayList = ArrayList<OrderItem>()
    var bidOrderAdapter: BidOrderAdapter? = null
    var userId = 0
    var chatId = ""
    var userName = ""
    var userImage = ""
    var dialog : Dialog?=null

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
                //Toast.makeText(this, "Toast", Toast.LENGTH_LONG).show()
                editDialog()
               /*  dialog = Dialog(this)
                dialog?.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                dialog?.setContentView(R.layout.biddetailsalertbox)
                dialog?.setCancelable(true)
                *//*val inflater = LayoutInflater.from(this@BidDetail)
                val v1 = inflater.inflate(R.layout.biddetailsalertbox, null)
                val deleteDialog = AlertDialog.Builder(this@BidDetail).create()
                deleteDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                deleteDialog.setView(v1)*//*
//                val btncontinue = dialog?.findViewById<com.stalkstock.utils.custom.TitiliumBoldButton>(R.id.submitbutton)
//                val edtPrice =
//                    dialog?.findViewById<com.stalkstock.utils.custom.TitiliumBoldEditText>(R.id.edtSellingPrice)
//                val edtSaleTerms =
//                    dialog?.findViewById<com.stalkstock.utils.custom.TitiliumBoldEditText>(R.id.edtSellngDesc)

//                btncontinue?.setOnClickListener { view ->
//
//                    if (edtPrice?.text.toString().isEmpty()) {
//                        edtPrice?.requestFocus()
//                        edtPrice?.error = resources.getString(R.string.please_enter_sale_price)
//                    } else if (edtSaleTerms?.text.toString().isNullOrEmpty()) {
//                        edtPrice?.requestFocus()
//                        edtPrice?.error = resources.getString(R.string.please_enter_sale_terms)
//                    } else {
//                        val hashMap = HashMap<String, RequestBody>()
//                        hashMap["bidId"] = mUtil.createPartFromString(bidId)
//                        hashMap["amount"] = mUtil.createPartFromString(edtPrice?.text.toString())
//                        hashMap["description"] = mUtil.createPartFromString(edtSaleTerms?.text.toString())
//                        viewModel.vendorAcceptBid(this, true, hashMap)
//                        viewModel.mResponse.observe(this, this)
//
//                        bidamt.visibility = View.VISIBLE
//                        biddisc.visibility = View.VISIBLE
//                        placebid_button.tag = 1
//                        placebid_button.text = "Place Bid"
//                        if (placebid_button.text.toString() == "Place Bid") {
//                            placebid_button.text = "Edit Bid"
//                            view.tag = 1 //pause
//                        } else {
//                            val status = view.tag as Int
//                            if (status == 1) {
//                                view.tag = 0 //pause
//                            } else {
//                                placebid_button.text = "Edit Bid"
//                                view.tag = 1 //pause
//                            }
//                        }
//
//
//                    }
//                    dialog?.dismiss()
//                }
                dialog?.show()
*//*
                val dialog = PlaceBidDialogFragment(this)
                dialog.show(supportFragmentManager,"postDelete")
*/
            }
        }
    }

    fun placeBid(hashMap: java.util.HashMap<String, RequestBody>) {
        viewModel.vendorAcceptBid(this, true, hashMap)
        viewModel.mResponse.observe(this, this)

        bidamt.visibility = View.VISIBLE
        biddisc.visibility = View.VISIBLE
        placebid_button.tag = 1
        placebid_button.text = "Place Bid"
        if (placebid_button.text.toString() == "Place Bid") {
            placebid_button.text = "Edit Bid"

        } else {
            placebid_button.text = "Edit Bid"

        }
    }

    private fun editDialog() {
        dialog = Dialog(this)
       // dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.setContentView(R.layout.biddetailsalertbox)
        dialog!!.setCancelable(true)

        val btnSubmit = dialog!!.findViewById<com.stalkstock.utils.custom.TitiliumBoldButton>(R.id.submitbutton)
        val etEditPrice = dialog!!.findViewById<com.stalkstock.utils.custom.TitiliumBoldEditText>(R.id.edtSellingPrice)
        val etEditDesc = dialog!!.findViewById<com.stalkstock.utils.custom.TitiliumBoldEditText>(R.id.edtSellngDesc)

        btnSubmit.setOnClickListener {
            dialog!!.dismiss()

           /* if (!mValidationClass!!.isNetworkConnected()) {
                showAlerterRed(resources.getString(R.string.no_internet))
            } else {
                if (etEditPrice.text.toString().isEmpty()) {

                } else {
                    val map = HashMap<String, String>()
                    map.put("id", productId)
                    map.put("mrp", etEditPrice.text.toString().trim())

                    viewModel.editPriceAPI(this, true, map)
                    viewModel.mResponse.observe(this, this)
                }
            }*/

            //   edit2Dialog()
        }
        dialog!!.show()
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

    private fun setBidData(body: BidData) {
        userId = body.commercialDetail.id
        chatId = body.chatId.toString()
        requestid.text = "Request Id: " + body.requestNo
        bidqu.text = "Quantity : " + body.requestNo
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