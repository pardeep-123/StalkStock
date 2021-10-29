package com.stalkstock.vender.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.vender.Model.VendorOrderListResponse
import com.stalkstock.vender.adapter.NewOrderAdapter
import com.stalkstock.vender.vendorviewmodel.VendorViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_new_order_list.*
import java.util.*
import kotlin.collections.ArrayList

class NewOrderList : AppCompatActivity(), Observer<RestObservable> {
    var mcontext: Context? = null
    var newOrderAdapter: NewOrderAdapter? = null
    val viewModel: VendorViewModel by viewModels()
    private var reset = false
    private var currentOffset = 0
    private val mOrderArrayList: ArrayList<VendorOrderListResponse.Body> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_order_list)

        val text = intent.getStringExtra("key")
        appbar_name.text = text
        mcontext = this@NewOrderList
        newOrderAdapter = NewOrderAdapter(mcontext, text,mOrderArrayList)
        neworder_recyclerview.adapter = newOrderAdapter
        orderlist_backarrow.setOnClickListener { onBackPressed() }
        neworder_recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && mOrderArrayList.size > 9)
                        getOrderList()
                }
            }
        })
        viewModel.mResponse.observe(this, this)
    }

    override fun onResume() {
        super.onResume()
        reset = true
        getOrderList()
    }

    private fun getOrderList() {
        if (reset) {
            currentOffset = 0
            mOrderArrayList.clear()
        }
        val hashMap = HashMap<String, String>()
        hashMap["offset"] = currentOffset.toString()
        hashMap["limit"] = "10"
        when {
            appbar_name.text.toString() == "New Orders" -> {
                hashMap["status"] = "0"
            }
            appbar_name.text.toString() == "In Progress" -> {
                hashMap["status"] = "1"
            }
            appbar_name.text.toString() == "Ready for Pickup" -> {
                hashMap["status"] = "2"
            }
        }
        hashMap["type"] = "0"    // 0=>current 1=>past
        viewModel.orderListVendorApi(this, true, hashMap)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is VendorOrderListResponse) {
                    val mResponse: VendorOrderListResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 10
                        mOrderArrayList.addAll(mResponse.body)
                        newOrderAdapter!!.notifyDataSetChanged()
                        reset = false

                        tvNoOrders.visibility = if(mOrderArrayList.isEmpty()) View.VISIBLE else View.GONE

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