package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.stalkstockcommercial.ui.paymnet.AddNewCard
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.CommericalOrderPlaceResponse
import com.stalkstock.commercial.view.model.Sendbidresponse
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.added_product.*
import kotlinx.android.synthetic.main.select_payment.*
import org.json.JSONArray
import org.json.JSONObject

class SelectPayment : AppCompatActivity(), Observer<RestObservable> {

    private val homeModel: HomeViewModel by viewModels()
    var card = ""
    var type = ""
    var firstname = ""
    var lastname = ""
    var bidId = 0
    var vendorId = 0
    var rs = ""
    var payment = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_payment)


        if (intent.hasExtra("card")) {
            card = intent.getStringExtra("card")!!
        }

        firstname = intent.getStringExtra("firstname").toString()
        lastname = intent.getStringExtra("lastname").toString()
        bidId = intent.getIntExtra("bidId", 0)
        vendorId = intent.getIntExtra("vendorId", 0)
        rs = intent.getStringExtra("rs").toString()

        oneone.setOnClickListener {
            if(!type.equals("first")){
                type = "first"
                oneone.setImageResource(R.drawable.radio_fill)
                onetwo.setImageResource(R.drawable.radio_circle)
            }
        }
        onetwo.setOnClickListener {
            if (!type.equals("sec")) {
                type = "sec"
                onetwo.setImageResource(R.drawable.radio_fill)
                oneone.setImageResource(R.drawable.radio_circle)
            }
        }
        ivBackPayment.setOnClickListener { onBackPressed() }
        btn_preview.setOnClickListener {
            startActivity(Intent(this, AddNewCard::class.java))

            /* if(card.isNotEmpty()){
             onBackPressed()}
         else
             {
                 startActivity(Intent(this,AddNewCard::class.java))
             }*/
        }
        btn_checkout.setOnClickListener {
            if (type=="sec"){
                payment = 1
            }
            else if (type=="first"){
                payment = 0
            }
            else{
                payment = 2
            }
            placeOrderApi()

            startActivity(Intent(this, PaymentStatus::class.java)) }
    }

    private fun placeOrderApi() {

      if (payment == 2 ){
          Toast.makeText(this,"Please Select Payment Method",Toast.LENGTH_SHORT).show()
      }
        else if (card.isEmpty()){
          Toast.makeText(this,"Please Add Card",Toast.LENGTH_SHORT).show()
      }
        else{
          val data = HashMap<String,Any>()
          data.put("vendorId",vendorId)
          data.put("netAmount",rs)
          data.put("shippingCharges",0)
          data.put("shopCharges",0)
          data.put("paymentMethod",payment)
          data.put("total",rs)
          data.put("isSelfpickup",1)
          data.put("cardId",card)
          data.put("bidId",bidId)

          homeModel.orderPlace(this, data,true)
          homeModel.homeResponse.observe(this, this)
      }

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {
                if (it.data is CommericalOrderPlaceResponse) {
                    val mResponse: CommericalOrderPlaceResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        val intent = Intent(this, PaymentStatus::class.java)
                        startActivity(intent)
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
