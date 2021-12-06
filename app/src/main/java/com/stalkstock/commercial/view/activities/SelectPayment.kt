package com.stalkstock.commercial.view.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.live.stalkstockcommercial.ui.paymnet.AddNewCard
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.model.CommericalOrderPlaceResponse
import com.stalkstock.common.model.PayPalWebResponse
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.select_payment.*

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
    var deliveryCharges=""
    var shopCharges=""
    var paymentMethod=""
    var transactionId=""
    var totalAmount=0F
    val driverViewModel: DriverViewModel by viewModels()



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
        deliveryCharges = intent.getStringExtra("deliveryCharges").toString()
        shopCharges = intent.getStringExtra("shopCharges").toString()
        totalAmount= (deliveryCharges.toFloat()+ shopCharges.toFloat()+rs.toFloat()).toFloat()

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
                payment = 3
            }
            else if (type=="first"){
                payment = 1
            }
            else{
                payment = 2
            }

            if(payment==1 ){
                if(card=="0") {
                    AppUtils.showErrorAlert(
                        this,
                        "Please add your card first"
                    )
                }else{
                    placeOrderApi()
                }
            }else if (payment == 2 ){
                Toast.makeText(this,"Please Select Payment Method",Toast.LENGTH_SHORT).show()
            }else{
                getPayPalWebLink()
            }


          //  startActivity(Intent(this, PaymentStatus::class.java))
        }
    }

    private fun getPayPalWebLink() {
        val hashMap = HashMap<String, Any>()
        hashMap.put("totalAmount", totalAmount)
        driverViewModel.getPayPalWebLink(this, true, hashMap)
        driverViewModel.mResponse.observe(this, this)

    }

    private fun placeOrderApi() {

     /* else if (card.isEmpty()){
          Toast.makeText(this,"Please Add Card",Toast.LENGTH_SHORT).show()
      }*/
        val data = HashMap<String,Any>()
        if(payment==1){
            data["cardId"] = card
        }else{
            data["transactionId"] = transactionId
        }
        data["vendorId"] = vendorId
        data["netAmount"] = rs
        data["shippingCharges"] = deliveryCharges
        data["shopCharges"] = shopCharges
        data["paymentMethod"] = payment
        data["total"] = totalAmount
        data["isSelfpickup"] = 0
        data["bidId"] = bidId

        homeModel.orderPlace(this, data,true)
        homeModel.homeResponse.observe(this, this)


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

                if (it.data is PayPalWebResponse) {
                    val mResponse: PayPalWebResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        for (i in 0 until mResponse.body.result.links.size) {
                            if (mResponse.body.result.links[i].rel == "approve") {
                                showebview(mResponse.body.result.links[i].href)
                            }
                        }
                    } else {
                        AppUtils.showErrorAlert(
                            this,
                            mResponse.message.toString()
                        )
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

    fun showebview(url: String) {
        val llPadding = 30
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("PayPal")
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(this)
        tvText.text = "Please wait..."
        tvText.setTextColor(Color.parseColor("#019243"))
        tvText.textSize = 16f
        tvText.layoutParams = llParam
        progressBar.visibility = View.VISIBLE
        tvText.visibility = View.VISIBLE
        ll.addView(progressBar)
        ll.addView(tvText)
        val wv: WebView = object : WebView(this) {
            override fun onCheckIsTextEditor(): Boolean {
                return true
            }
        }
        wv.loadUrl(url)
        wv!!.settings.javaScriptEnabled = true
        wv.settings.javaScriptCanOpenWindowsAutomatically = true
        wv.settings.domStorageEnabled = true
        wv.settings.allowFileAccess = true
        wv.settings.allowContentAccess = true
        wv.settings.setGeolocationEnabled(true)
        wv.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
                tvText.visibility = View.GONE

            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                Log.e("url", url)
                view.loadUrl(url)
                if (url.contains("http://3.13.214.27:8800/api/successStalkAndStockUrl")) {
                    // if (url.contains("http://192.168.1.119:8800/api/successStalkAndStockUrl")) {

                    val uri: Uri =
                        Uri.parse(url)
                    val server: String? = uri.getAuthority()
                    val path: String? = uri.getPath()
                    val protocol: String? = uri.getScheme()
                    val args: Set<String> = uri.getQueryParameterNames()

                    val id = uri.getQueryParameter("token").toString()
                    transactionId = id
                    // mObject!!.transactionId = add transaction id here
                    placeOrderApi()
                    //place order api
                }
                return true
            }
        }

        ll.addView(wv)
        alert.setView(ll)
        alert.setNegativeButton("Close") { dialog, id -> dialog.dismiss() }
        alert.show()
    }

}
