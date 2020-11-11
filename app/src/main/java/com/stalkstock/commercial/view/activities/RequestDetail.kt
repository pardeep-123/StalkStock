package com.live.stalkstockcommercial.ui.view.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.live.stalkstockcommercial.ui.paymnet.SelectPayment
import com.live.stalkstockcommercial.ui.product.AddedProduct
import com.live.stalkstockcommercial.ui.product.RequestProductAdapter
import com.live.stalkstockcommercial.ui.view.activities.Chat
import com.stalkstock.R
import com.stalkstock.consumer.activities.PaymentActivity
import kotlinx.android.synthetic.main.request_detail.*

class RequestDetail : AppCompatActivity() {

    var list : ArrayList<AddedProduct.RequestProductData> = ArrayList()
    var listBids : ArrayList<BidsData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_detail)


        list.add(AddedProduct.RequestProductData("Meat", "Bacon Grill", "10", "Kg",false,false))
        list.add(AddedProduct.RequestProductData("Meat", "Bacon Normal", "8", "Kg",false,false))


        rvRequestProducts.adapter = RequestProductAdapter(list)


        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))
        listBids.add(BidsData("Jamie jai","McDonald's","$80.50","Accept"))


        val adapter  = BidsRequestAdapter(listBids)
        rvRequestBids.adapter = adapter

        adapter.onPerformClick(object : BidsRequestAdapter.ClickItem{
            override fun clicked(position: Int) {

                tvChat.visibility = View.VISIBLE
                cvBidder.visibility = View.VISIBLE
                rvRequestBids.visibility = View.GONE
                btnAccept.visibility = View.VISIBLE
            }
        })


        clicks()


    }

    private fun clicks() {
        btnAccept.setOnClickListener {
            if(btnAccept.text !="Pay Now") {
                btnAccept.text = "Pay Now"
                tvPrice.setTextColor(resources.getColor(R.color.green_colour))
                ivTick.visibility = View.VISIBLE
            }
            else
            {
                startActivity(Intent(this, SelectPayment::class.java))
            }
            }

        ivBusinessEdit.setOnClickListener {
            finish()
        }


        tvChat.setOnClickListener {
            startActivity(Intent(this, Chat::class.java))
        }
    }


    data class BidsData(var name: String = "",var detail: String = "",var rs: String = "",var accept: String = "")

}
