package com.stalkstock.advertiser.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
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
import com.stalkstock.R
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.ThanksActivity
import com.stalkstock.consumer.model.*
import com.stalkstock.utils.others.AppUtils
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.toolbar.*

class PaymentActivity : AppCompatActivity(), View.OnClickListener, Observer<RestObservable> {
    val mContext: Context = this
    var click = 0
    var mMap: HashMap<String, String>? = null
    var mObject: PlaceOrderModel? = null
    val viewModel: HomeViewModel by viewModels()
    var mPaymentType = "1"  // 0=>Wallet 1=>Card 2=>paypal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_payment)

        tv_heading.text = "Payment"
        iv_back.setOnClickListener(this)
        btn_checkout.setOnClickListener(this)
        btn_preview.setOnClickListener(this)
        firstclick.setOnClickListener(this)
        secondclick.setOnClickListener(this)

        if (intent.hasExtra("orderdata")) {
//            mMap = intent.getSerializableExtra("orderdata") as HashMap<String, String>
            mObject = intent.getSerializableExtra("orderdata") as PlaceOrderModel
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_back -> {
                finish()
            }
            R.id.btn_checkout -> {
                if (click == 0) {
                    click = 1;
                    btn_checkout.text = "Pay Now"
                } else {
                   // if (MyApplication.instance.getString("usertype").equals("1")) {
                        if (mObject != null && mPaymentType=="1"){
                            mObject!!.paymentMethod = mPaymentType
                            placeOrderApi()
                    } else {
                           // mObject!!.paymentMethod = mPaymentType
                            Toast.makeText(this, "In progress", Toast.LENGTH_SHORT).show()
                        //add paypal functionality
//                        val intent = Intent(mContext, ThankyouActivity::class.java)
//                        startActivity(intent)
                    }
                }

            }
            R.id.btn_preview -> {
                val intent = Intent(mContext, AddNewCardActivity::class.java)
                startActivity(intent)
            }
            R.id.firstclick -> {
                mPaymentType = "1"
                oneone.setImageResource(R.drawable.radio_fill)
                onetwo.setImageResource(R.drawable.radio_circle)
            }
            R.id.secondclick -> {
                mPaymentType = "3"
                oneone.setImageResource(R.drawable.radio_circle)
                onetwo.setImageResource(R.drawable.radio_fill)
            }
        }
    }

    private fun placeOrderApi() {

        viewModel.placeOrderApi(this, true, mObject!!)
        viewModel.homeResponse.observe(this, this)
    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is OrderPlaceResponse) {
                    val mResponse: OrderPlaceResponse = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        val intent = Intent(mContext, ThanksActivity::class.java)
                        startActivity(intent)
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
                    if (it.error!!.toString().contains("Data not found")) {
                        val intent = Intent(this, MainConsumerActivity::class.java)
                        intent.putExtra("is_open", "4")
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()

                    } else
                        Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT)
                            .show()
//

//                    showAlerterRed()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    fun showebview(url:String)
    {
        val llPadding = 30
        val alert: AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("PayPal")
        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(this)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
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
                Log.e("url",url)
                view.loadUrl(url)
                if (url.contains("http://3.13.214.27:8800/api/successStalkAndStockUrl")) {
                   // mObject!!.transactionId = add transaction id here
                    placeOrderApi()
                    //place order api
                    //cartPreseneter.placeorder(requireArguments().getString("type")!!,requireArguments().getString("cart_id")!!,requireArguments().getString("quantity")!!,requireArguments().getString("amount")!!,requireArguments().getString("address_id")!!,paymentcode)
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