package com.stalkstock.vender.ui

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.MyApplication
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.extention.checkObjectNull
import com.stalkstock.utils.extention.checkStringNull
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.OrderDetailVendorResponse
import com.stalkstock.vender.Model.VendorCommonModel
import com.stalkstock.vender.adapter.MyVendorOrderProductAdapter
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_order_details.*
import java.util.*

class OrderDetails : AppCompatActivity(), Observer<RestObservable> {
    val viewModel: VendorViewModel by viewModels()
    var tv_deli_by: TextView? = null
    var tv_deli_to: TextView? = null
    var tv_deli_by_value: TextView? = null
    var mStatus = 0  // 0 pending 1 in progress 2 ready for pickup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)
        tv_deli_by = findViewById(R.id.tv_deli_by)
        tv_deli_to = findViewById(R.id.tv_deli_to)
        tv_deli_by_value = findViewById(R.id.tv_deli_by_value)

        if (intent.hasExtra("key")) {
            val stringExtra = intent.getStringExtra("key")
            when {
                stringExtra.equals("New") -> {
                    mStatus = 0
                    text_detailes4.setTextColor(resources.getColor(R.color.red_bid))
                    text_detailes4.visibility = View.VISIBLE
                    text_detailes4.text = stringExtra
                    ltStatusChange.setOnClickListener { dialogSpinner() }
                }
                stringExtra.equals("In Progress") -> {
                    text_detailes2.visibility = View.VISIBLE
                    mStatus = 1
                    text_detailes2.text = stringExtra
                    ltStatusChange.setOnClickListener { dialogSpinner() }
                }
                stringExtra.equals("Ready For Pickup") -> {
                    text_detailes3.visibility = View.VISIBLE
                    text_detailes3.text = stringExtra
                    mStatus = 3
                    ltStatusChange.setOnClickListener { dialogSpinner() }
                }
                stringExtra.equals("Delivered") -> {
                    text_detailes4.visibility = View.VISIBLE
                    text_detailes4.text = stringExtra
                    ltStatusChange.setOnClickListener {  }
                }
                stringExtra.equals("Rejected") -> {
                    text_detailes4.setTextColor(resources.getColor(R.color.red_bid))
                    text_detailes4.visibility = View.VISIBLE
                    text_detailes4.text = stringExtra
                    ltStatusChange.setOnClickListener {  }
                }
            }
        }

        order_details_backarrow.setOnClickListener { onBackPressed() }
        if (intent.hasExtra("orderId"))
            getOrderDetail(intent.getStringExtra("orderId")!!)
    }

    private fun dialogSpinner() {

        val dialogSuccessful = Dialog(Objects.requireNonNull(this), R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.dialog_spinner)
        dialogSuccessful.setCancelable(true)
        Objects.requireNonNull(dialogSuccessful.window)!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialogSuccessful.setCanceledOnTouchOutside(true)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)

        val ltStatus1 = dialogSuccessful.findViewById<LinearLayout>(R.id.ltStatus1)
        val ltStatus2 = dialogSuccessful.findViewById<LinearLayout>(R.id.ltStatus2)
        val ltStatus3 = dialogSuccessful.findViewById<LinearLayout>(R.id.ltStatus3)
        val ltStatus4 = dialogSuccessful.findViewById<LinearLayout>(R.id.ltStatus4)

        if(mStatus==0){
            ltStatus4.visibility=View.VISIBLE
            ltStatus1.visibility=View.VISIBLE
            ltStatus2.visibility=View.VISIBLE
            ltStatus3.visibility=View.VISIBLE
        }else if(mStatus==1){
            ltStatus1.visibility=View.GONE
            ltStatus2.visibility=View.VISIBLE
            ltStatus3.visibility=View.VISIBLE
            ltStatus4.visibility=View.GONE
        } else if(mStatus==2){
            ltStatus3.visibility=View.VISIBLE
            ltStatus1.visibility=View.GONE
            ltStatus2.visibility=View.GONE
            ltStatus4.visibility=View.GONE
        }else{
            ltStatus3.visibility=View.VISIBLE
            ltStatus1.visibility=View.GONE
            ltStatus2.visibility=View.GONE
            ltStatus4.visibility=View.GONE
        }

        ltStatus1.setOnClickListener {
            changeStatus("1") //accept
            dialogSuccessful.dismiss()
        }
        ltStatus2.setOnClickListener {
            changeStatus("2") //Packed
            dialogSuccessful.dismiss()
        }
        ltStatus3.setOnClickListener {
            changeStatus("4") //user picked order from restaurant     order delivered
            dialogSuccessful.dismiss()
        }
        ltStatus4.setOnClickListener {
            changeStatus("6") //order rejected
            dialogSuccessful.dismiss()
        }

        dialogSuccessful.show()

    }

    private fun changeStatus(s: String) {
        val hashMap = HashMap<String, String>()
        if (intent.hasExtra("orderId")) {
            hashMap["orderId"] = intent.getStringExtra("orderId").toString()
            hashMap["status"] = s
            viewModel.vendorChangeOrderStatus(this, true, hashMap)
            viewModel.mResponse.observe(this, this)
        }
    }

    override fun onResume() {
        super.onResume()
        if (MyApplication.instance.getString("usertype") == "3") {
            if (intent.getStringExtra("show") != null) {
                tv_deli_by!!.visibility = View.VISIBLE
                tv_deli_by_value!!.visibility = View.VISIBLE
                tv_deli_by!!.text = "DELIVERED BY"
                tv_deli_to!!.text = "DELIVERED TO"
            } else {
                tv_deli_by!!.visibility = View.GONE
                tv_deli_by_value!!.visibility = View.GONE
                tv_deli_to!!.text = "DELIVERED TO"
            }
        }
    }

    private fun getOrderDetail(orderId: String) {
        val hashMap = HashMap<String, String>()
        hashMap["orderId"] = orderId
        viewModel.orderDetailVendorApi(this, true, hashMap)
        viewModel.mResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is OrderDetailVendorResponse) {
                    val mResponse: OrderDetailVendorResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        tvName.text = mResponse.body.firstName + " " + mResponse.body.lastName
                        tvOrderNumber.text = mResponse.body.orderNo
                        tvOrderNumber2.text = mResponse.body.orderNo
                        mRecyclerView.adapter =
                            MyVendorOrderProductAdapter(this, mResponse.body.orderItems)
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
                                tv_deli_to!!.visibility = View.GONE
                            }
                        } else {
                            tvDeliveryLocation.visibility = View.GONE
                            tv_deli_to!!.visibility = View.GONE
                        }
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
                if (it.data is VendorCommonModel)
                {
                    val mResponse: VendorCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        AppUtils.showSuccessAlert(this, mResponse.message)
                        Handler(Looper.getMainLooper()).postDelayed({
                            finish()
                        }, 2000)
                    }
                    else {
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