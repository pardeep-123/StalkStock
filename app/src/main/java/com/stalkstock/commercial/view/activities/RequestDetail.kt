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
import com.bumptech.glide.Glide
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.adapters.BidsRequestAdapter
import com.stalkstock.commercial.view.adapters.RequestProductAdapter
import com.stalkstock.commercial.view.model.BidingDetailResponse
import com.stalkstock.utils.custom.TitiliumBoldTextView
import com.stalkstock.utils.custom.TitiliumRegularTextView
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.request_detail.*
import kotlinx.android.synthetic.main.request_product_adapter.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set

class RequestDetail : AppCompatActivity(), Observer<RestObservable> {

    var userId = 0
    var chatId = ""
    var userName = ""
    var userImage = ""
    private val homeModel: HomeViewModel by viewModels()
    var cardId=""
    var deliveryCharges=""

    var detail = ArrayList<BidingDetailResponse.VendorBidingRequest>()
    var list: ArrayList<AddedProduct.RequestProductData> = ArrayList()
    var listBids: ArrayList<BidingDetailResponse.VendorBidingRequest> = ArrayList()

    //    var bidUser : ArrayList<BidingDetailResponse.VendorDetail> = ArrayList()
    private var orderItemList: ArrayList<BidingDetailResponse.OrderItem> = ArrayList()
    var bidId: Int = 0

    var vendorBidAdapter:BidsRequestAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_detail)

        val tvName = findViewById<TitiliumBoldTextView>(R.id.tvName)
        val tvPrices = findViewById<TitiliumBoldTextView>(R.id.tvPrices)
        val tvDetail = findViewById<TitiliumRegularTextView>(R.id.tvDetail)
        val civImage = findViewById<CircleImageView>(R.id.civImage)

        bidId = intent.getIntExtra("bidId", 0)

        getBidingDetilsApi()

        list.add(
            AddedProduct.RequestProductData(
                "Item Brand",
                "Item Name",
                "Quantity",
                "",
                "",
                "Unit of Measurement",
                false,
                false
            )
        )
//        list.add(AddedProduct.RequestProductData("Meat", "Bacon Grill", "10", "Kg","", false, false))
//        list.add(AddedProduct.RequestProductData("Meat", "Bacon Normal", "8", "Kg","", false, false))
//        rvRequestProducts.adapter = RequestProductAdapter(list)

        /*    listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))*/

        vendorBidAdapter = BidsRequestAdapter()
        rvRequestBids.adapter = vendorBidAdapter
        vendorBidAdapter?.listBids= listBids

        vendorBidAdapter?.onPerformClick(object : BidsRequestAdapter.ClickItem {
            override fun clicked(position: Int, items: BidingDetailResponse.VendorBidingRequest) {

                tvChat.visibility = VISIBLE
                cvBidder.visibility = VISIBLE
                rvRequestBids.visibility = GONE
                btnAccepts.visibility = VISIBLE

                detail.add(items)

                tvName.setText(items.vendorDetail.firstName + " " + items.vendorDetail.lastName)
                tvPrices.setText("$" + items.amount)
                tvDetail.setText(items.vendorDetail.shopName)
                Glide.with(this@RequestDetail).load(items.vendorDetail.image).into(civImage)

            }
        })

        clicks()

    }

    private fun clicks() {
        btnAccepts.setOnClickListener {
            if (btnAccepts.text != "Pay Now") {
                btnAccepts.text = "Pay Now"
                tvPrices.setTextColor(resources.getColor(R.color.green_colour))
                ivTick.visibility = View.VISIBLE
            } else {
                val intent = Intent(this, SelectPayment::class.java)
                intent.putExtra("firstname", detail[0].vendorDetail.firstName)
                intent.putExtra("lastname", detail[0].vendorDetail.lastName)
                intent.putExtra("bidId", detail[0].bidId)
                intent.putExtra("vendorId", detail[0].vendorId)
                intent.putExtra("rs", detail[0].amount)
                intent.putExtra("deliveryCharges", deliveryCharges)
                intent.putExtra("shopCharges", detail[0].vendorDetail.shopCharges)
                intent.putExtra("card", cardId)
                startActivity(intent)

            }
        }

        ivBusinessEdit.setOnClickListener {
            onBackPressed()
        }

        tvChat.setOnClickListener {
            val intent = Intent(this, Chat::class.java)

            intent.putExtra("screen_type", "bid")
            intent.putExtra("id", bidId.toString())
            intent.putExtra("userId", detail[0].vendorDetail.id)
            intent.putExtra("chatId", chatId)
            intent.putExtra("userName", detail[0].vendorDetail.firstName +" "+detail[0].vendorDetail.lastName)
            intent.putExtra("userImage", detail[0].vendorDetail.image)
            intent.putExtra("param_name", "bidId")

            startActivity(intent)
        }
    }

    data class BidsData(
        var firstname: String = "", var lastname: String = "", var detail: String = "",
        var rs: String = "", var accept: String = "", var image: String = "", var vendorId: Int = 0,
        var bidId: Int = 0,var deliveryCharges:String="",var shopCharges:String=""
    )

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is BidingDetailResponse) {
                    val mResponse: BidingDetailResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        chatId= mResponse.body.chatId.toString()
                        deliveryCharges= mResponse.body.deliveryCharges
                        setData(mResponse)
                        cardId= mResponse.body.cardId
                        chatId = mResponse.body.chatId.toString()
                        orderItemList.clear()
                        orderItemList.addAll(it.data.body.orderItems)
                        layShpList.visibility = VISIBLE
                        rl_edit.visibility = GONE
                        rl_delete.visibility = GONE
                        tvType.text = "Item Name"
                        tvQuantity.text = "Quantity"
                        tvQuantityType.text = "U.O.M"
                        rvRequestProducts.adapter = RequestProductAdapter(list, orderItemList)
                        listBids.clear()

                        listBids.addAll(mResponse.body.vendorBidingRequests)
                        vendorBidAdapter?.notifyDataSetChanged()


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

        tvRequest.text = "Request ID:" + " " + mResponse.body.requestNo
        tvCreatedDateB.text = AppUtils.changeDateFormat(mResponse.body.createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat3,
            GlobalVariables.DATEFORMAT.DateTimeFormat2)
//        userId = mResponse.body.vendorBidingRequest.vendorDetail.id
//        userName =
//            mResponse.body.vendorBidingRequest.vendorDetail.firstName + " " + mResponse.body.vendorBidingRequest.vendorDetail.lastName
//        userImage = mResponse.body.vendorBidingRequest.vendorDetail.image
        //userImage= mResponse.body.
    }

    /*   override fun onResume() {
           super.onResume()
           getBidingDetilsApi()
       }*/

    private fun getBidingDetilsApi() {
        val map = HashMap<String, Int>()
        map["bidId"] = bidId
        homeModel.getBidingDetail(this, true, map)
        homeModel.homeResponse.observe(this, this)
    }

    override fun onRestart() {
        super.onRestart()
        getBidingDetilsApi()

    }
}
