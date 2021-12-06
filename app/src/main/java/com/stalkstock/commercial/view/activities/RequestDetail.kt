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
import java.text.SimpleDateFormat
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

    var detail = ArrayList<BidsData>()
    var list: ArrayList<AddedProduct.RequestProductData> = ArrayList()
    var listBids: ArrayList<BidsData> = ArrayList()
    var bidsreq: ArrayList<BidingDetailResponse.VendorBidingRequest> = ArrayList()

    //    var bidUser : ArrayList<BidingDetailResponse.VendorDetail> = ArrayList()
    private var orderItemList: ArrayList<BidingDetailResponse.OrderItem> = ArrayList()
    var bidId: Int = 0

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
                "Unit of Measurement",
                false,
                false
            )
        )
        list.add(AddedProduct.RequestProductData("Meat", "Bacon Grill", "10", "Kg","", false, false))
        list.add(AddedProduct.RequestProductData("Meat", "Bacon Normal", "8", "Kg","", false, false))
//        rvRequestProducts.adapter = RequestProductAdapter(list)

        /*    listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
            listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))*/

        val adapter = BidsRequestAdapter(listBids)
        rvRequestBids.adapter = adapter

        adapter.onPerformClick(object : BidsRequestAdapter.ClickItem {
            override fun clicked(position: Int, items: BidsData) {

                tvChat.visibility = VISIBLE
                cvBidder.visibility = VISIBLE
                rvRequestBids.visibility = GONE
                btnAccepts.visibility = VISIBLE

                detail.add(
                    BidsData(
                        items.firstname,
                        items.lastname,
                        items.detail,
                        items.rs,
                        items.accept,
                        items.image,
                        items.vendorId,
                        items.bidId,
                        items.deliveryCharges,
                        items.shopCharges
                    )
                )

                tvName.setText(items.firstname + " " + items.lastname)
                tvPrices.setText("$" + items.rs)
                tvDetail.setText(items.detail)
                Glide.with(this@RequestDetail).load(items.image).into(civImage)

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
                intent.putExtra("firstname", detail[0].firstname)
                intent.putExtra("lastname", detail[0].lastname)
                intent.putExtra("bidId", detail[0].bidId)
                intent.putExtra("vendorId", detail[0].vendorId)
                intent.putExtra("rs", detail[0].rs)
                intent.putExtra("deliveryCharges", detail[0].deliveryCharges)
                intent.putExtra("shopCharges", detail[0].shopCharges)
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
            intent.putExtra("userId", userId)
            intent.putExtra("chatId", chatId)
            intent.putExtra("userName", userName)
            intent.putExtra("userImage", userImage)
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

                        setData(mResponse)
                        cardId= mResponse.body.cardId
                        chatId = mResponse.body.chatId.toString()
                        orderItemList.addAll(it.data.body.orderItems)
                        layShpList.visibility = VISIBLE
                        rl_edit.visibility = GONE
                        rl_delete.visibility = GONE
                        tvType.text = "Item Name"
                        tvQuantity.text = "Quantity"
                        tvQuantityType.text = "U.O.M"
                        rvRequestProducts.adapter = RequestProductAdapter(list, orderItemList)

                        listBids.add(
                            BidsData(
                                it.data.body.vendorBidingRequest.vendorDetail.firstName,
                                it.data.body.vendorBidingRequest.vendorDetail.lastName,
                                it.data.body.vendorBidingRequest.vendorDetail.shopName,
                                it.data.body.vendorBidingRequest.amount,
                                "Accept",
                                it.data.body.vendorBidingRequest.vendorDetail.image,
                                it.data.body.vendorBidingRequest.vendorId,
                                it.data.body.vendorBidingRequest.bidId,
                                it.data.body.deliveryCharges,
                                it.data.body.vendorBidingRequest.vendorDetail.shopCharges
                            )
                        )

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

        tvRequest.text = "Request ID:" + " " + mResponse.body.requestNo
        tvCreatedDateB.text = AppUtils.changeDateFormat(mResponse.body.createdAt,
            GlobalVariables.DATEFORMAT.DateTimeFormat3,
            GlobalVariables.DATEFORMAT.DateTimeFormat2)
        userId = mResponse.body.vendorBidingRequest.vendorDetail.id
        userName =
            mResponse.body.vendorBidingRequest.vendorDetail.firstName + " " + mResponse.body.vendorBidingRequest.vendorDetail.lastName
        userImage = mResponse.body.vendorBidingRequest.vendorDetail.image
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
}
