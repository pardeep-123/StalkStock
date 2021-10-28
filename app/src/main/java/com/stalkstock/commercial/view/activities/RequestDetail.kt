package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.stalkstock.commercial.view.adapters.RequestProductAdapter
import com.live.stalkstockcommercial.ui.view.activities.Chat
import com.stalkstock.commercial.view.adapters.BidsRequestAdapter
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.BidingDetailResponse
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.request_detail.*
import kotlinx.android.synthetic.main.request_product_adapter.*
import java.text.SimpleDateFormat
import java.util.HashMap

class RequestDetail : AppCompatActivity(), Observer<RestObservable> {
    private val homeModel: HomeViewModel by viewModels()
    var list : ArrayList<AddedProduct.RequestProductData> = ArrayList()
    var listBids : ArrayList<BidsData> = ArrayList()
    private var orderItemList: ArrayList<BidingDetailResponse.OrderItem> = ArrayList()
      var bidId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_detail)

        bidId = intent.getIntExtra("bidId",0)

        getBidingDetilsApi()

        list.add(AddedProduct.RequestProductData("Item Brand", "Item Name", "Quantity", "Unit of Measurement",false,false))
        list.add(AddedProduct.RequestProductData("Meat", "Bacon Grill", "10", "Kg",false,false))
        list.add(AddedProduct.RequestProductData("Meat", "Bacon Normal", "8", "Kg",false,false))

//        rvRequestProducts.adapter = RequestProductAdapter(list)

        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))

        val adapter  = BidsRequestAdapter(listBids)
        rvRequestBids.adapter = adapter

        adapter.onPerformClick(object : BidsRequestAdapter.ClickItem {
            override fun clicked(position: Int) {

                tvChat.visibility = VISIBLE
                cvBidder.visibility = VISIBLE
                rvRequestBids.visibility = GONE
                btnAccepts.visibility = VISIBLE
            }
        })

        clicks()

    }

    private fun clicks() {
        btnAccepts.setOnClickListener {
            if(btnAccepts.text !="Pay Now") {
                btnAccepts.text = "Pay Now"
                tvPrices.setTextColor(resources.getColor(R.color.green_colour))
                ivTick.visibility = View.VISIBLE
            }
            else
            {
                startActivity(Intent(this, SelectPayment::class.java))
            }
            }

        ivBusinessEdit.setOnClickListener {
            onBackPressed()
        }

        tvChat.setOnClickListener {
            startActivity(Intent(this, Chat::class.java))
        }
    }

    data class BidsData(var name: String = "",var detail: String = "",var rs: String = "",var accept: String = "")

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is BidingDetailResponse) {
                    val mResponse: BidingDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        setData(mResponse)
                        orderItemList.addAll(it.data.body.orderItems)
                        layShpList.visibility = VISIBLE
                        rl_edit.visibility = GONE
                        rl_delete.visibility = GONE
                        tvType.text = "Item Name"
                        tvQuantity.text = "Quantity"
                        tvQuantityType.text = "U.O.M"
                        rvRequestProducts.adapter = RequestProductAdapter(list,orderItemList)
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

    private fun setData(mResponse: BidingDetailResponse) {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val formatter = SimpleDateFormat("MMM dd, yyyy '|' HH:mm")
        val output: String = formatter.format(parser.parse(mResponse.body.createdAt))

        tvRequest.text = "Request ID:"+" "+mResponse.body.requestNo
        tvCreatedDateB.text = output
    }

 /*   override fun onResume() {
        super.onResume()
        getBidingDetilsApi()
    }*/

    private fun getBidingDetilsApi() {
        val map = HashMap<String, Int>()
        map["bidId"]= bidId
        homeModel.getBidingDetail(this, true,map)
        homeModel.homeResponse.observe(this, this)
    }
}
