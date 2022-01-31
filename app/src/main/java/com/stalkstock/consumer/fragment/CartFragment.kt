package com.stalkstock.consumer.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.AddNewCardActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.commercial.view.activities.ManageAddress
import com.stalkstock.common.model.PayPalWebResponse
import com.stalkstock.consumer.activities.MainConsumerActivity
import com.stalkstock.consumer.activities.ThanksActivity
import com.stalkstock.consumer.adapter.CartAdapter
import com.stalkstock.consumer.model.*
import com.stalkstock.driver.viewmodel.DriverViewModel
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.fragment_cart.*
import okhttp3.RequestBody
import java.lang.Exception
import java.text.DecimalFormat
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.set

/**
 * A simple [Fragment] subclass.
 */

class CartFragment : Fragment(), Observer<RestObservable>, View.OnClickListener {
    private lateinit var cartAdapter: CartAdapter
    lateinit var tvChange: TextView
    var views: View? = null
    var click = 0
    val mArrayLIst = ArrayList<PlaceOrderModel.OrderItem>()
    lateinit var btnStartShopping: Button
    lateinit var firstClick: LinearLayout
    lateinit var secondClick: LinearLayout
    lateinit var btnCheckout: Button
    lateinit var btnPreview: Button
    lateinit var recyceler: RecyclerView
    lateinit var ctx: Context
    var currentCartModel: ArrayList<ModelCartData.Body.CartData> = ArrayList()
    var mVendorId = ""
    var mTotalQuantity = 0
    var mNetAmount = "0"
    var cardId=0
    var mPaymentType = "1"  // 0=>Wallet 1=>Card 2=>paypal
    var mShippingCharges = "0"
    var mShopCharges = "0"
    var mTotalAmount = "0"
    var mSelfPickUp = "1"
    var totalAmountPayPal=0
    private var mAddressId = ""
    val driverViewModel: DriverViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container!!.context
        views = inflater.inflate(R.layout.fragment_cart, container, false)
        btnStartShopping = views!!.findViewById(R.id.btnStartShopping)
        firstClick= views!!.findViewById(R.id.firstClick)
        secondClick = views!!.findViewById(R.id.secondClick)
        btnCheckout = views!!.findViewById(R.id.btnCheckout)
        btnPreview = views!!.findViewById(R.id.btnPreview)
        tvChange = views!!.findViewById(R.id.tvChange)
        firstClick.setOnClickListener(this)
        secondClick.setOnClickListener(this)
        btnCheckout.setOnClickListener(this)
        btnPreview.setOnClickListener(this)

        tvChange.setOnClickListener {
            val intent = Intent(activity, ManageAddress::class.java)
            startActivity(intent)
        }

        btnStartShopping.setOnClickListener {
            startActivity(Intent(context,MainConsumerActivity::class.java))
        }

        recyceler = views!!.findViewById(R.id.recyclerCartItems)
        cartAdapter = CartAdapter(this, requireContext(), currentCartModel)
        recyceler.adapter = cartAdapter
        return views
    }

    override fun onResume() {
        super.onResume()
        getCartData()
    }

    private fun getCartData() {
        llMain.visibility=View.GONE
        btnCheckout.visibility=View.GONE
        btnPreview.visibility=View.GONE
        val mainConsumerActivity = activity as MainConsumerActivity
        viewModel.getUserCardDataAPI(mainConsumerActivity, true)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }

    val viewModel: HomeViewModel by viewModels()
        override fun onChanged(it: RestObservable?) {
            when {
                it!!.status == Status.SUCCESS -> {
                    llMain.visibility=View.VISIBLE
                    btnPreview.visibility=View.VISIBLE
                    btnCheckout.visibility=View.VISIBLE
                    btnStartShopping.visibility=View.GONE
                    tvNoCartData.visibility=View.GONE

                    if (it.data is ModelCartData) {
                        val mResponse: ModelCartData = it.data
                        if (mResponse.code == GlobalVariables.URL.code) {
                            setDataCart(mResponse)
                        } else {
                            AppUtils.showErrorAlert(
                                requireActivity(),
                                mResponse.message
                            ) } }

                    if (it.data is ModelAddToCart) {
                        val mResponse: ModelAddToCart = it.data
                        if (mResponse.code == GlobalVariables.URL.code) {
                            getCartData()
                        } else {
                            AppUtils.showErrorAlert(
                                requireActivity(),
                                mResponse.message
                            )
                        }
                    }
                    if (it.data is OrderPlaceResponse) {
                        val mResponse: OrderPlaceResponse = it.data
                        if (mResponse.code == GlobalVariables.URL.code) {
                            val intent = Intent(context, ThanksActivity::class.java)
                            startActivity(intent)
                        } else {
                            AppUtils.showErrorAlert(
                                requireActivity(),
                                mResponse.message.toString()
                            )
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
                                requireActivity(),
                                mResponse.message.toString()
                            )
                        }
                    }

                    if (it.data is UserCommonModel) {
                        val mResponse: UserCommonModel = it.data
                        if (mResponse.code == GlobalVariables.URL.code) {
                            getCartData()
                        } else {
                            AppUtils.showErrorAlert(ctx as Activity, mResponse.message)
                        }
                    }
                }
                it.status == Status.ERROR -> {
                    if (it.data != null) {
                        Toast.makeText(ctx, it.data as String, Toast.LENGTH_SHORT).show()
                    } else {
                        if (it.error!!.toString().contains("Data not found!")) {
                            try{
                                llMain.visibility=View.GONE
                                btnCheckout.visibility=View.GONE
                                btnPreview.visibility=View.GONE
                                btnStartShopping.visibility=View.VISIBLE
                                tvNoCartData.visibility=View.VISIBLE
                            }
                            catch (e:Exception)
                            {
                                e.printStackTrace()
                            }

                        } else
                            Toast.makeText(ctx, it.error.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                it.status == Status.LOADING -> {
                }
            }
        }

    private fun setDataCart(mResponse: ModelCartData) {
        for (model in currentCartModel) {
            val price = model.product.mrp.toFloat() * model.quantity
            val format = DecimalFormat("##.##").format(price)
            mArrayLIst.add(
                PlaceOrderModel.OrderItem(
                    model.product.mrp,
                    model.productId,
                    model.quantity,
                    format
                )
            )
        }

        if(mResponse.body.card!=null){
            cardId= mResponse.body.card.id
            ivCard.setImageResource(R.drawable.radio_fill)
            ivPaypal.setImageResource(R.drawable.radio_circle)
        }

        if (mResponse.body.shopDetail.deliveryType == 0) {
            ltPickup.visibility = View.VISIBLE
            ltDeliveryAddress.visibility = View.GONE
            mSelfPickUp = "1"
        }
        else {
            ltPickup.visibility = View.GONE
            ltDeliveryAddress.visibility = View.VISIBLE
            mSelfPickUp = "0"

            if(mResponse.body.address!=null) {

                mAddressId = mResponse.body.address.id.toString()
                var addressType = ""
                if (mResponse.body.address.type == "1")
                    addressType = "Home"
                if (mResponse.body.address.type == "2")
                    addressType = "Work"
                if (mResponse.body.address.type == "3")
                    addressType = "Other"
                item_count.text = "Deliver to $addressType"
                addressInCart.text = mResponse.body.address.geoLocation
            }
            else
            {
                item_count.text = getString(R.string.no_address_added)
                addressInCart.text = getString(R.string.no_address_added)
                tvChange.text = getString(R.string.add_new)
            }

        }

        var totalItemValue = 0.00f
        for (i in mResponse.body.cartData) {
            val mrp = i.product.mrp.toFloat()
            val singleItemTotal = i.quantity * mrp
            totalItemValue += singleItemTotal
            mTotalQuantity += i.quantity
            mVendorId = i.vendorId.toString()
        }
        val format = String.format("%.2f", totalItemValue).toString()
            //DecimalFormat("##.##").format(totalItemValue)
        Log.e("asdfadsfdas","=====$format==")
        txtItemTotal.text = "$$format"
        mNetAmount = format
        val shopCharges = mResponse.body.shopDetail.shopCharges.toFloat()
        val deliveryCharges = mResponse.body.deliveryCharges.value.toFloat()
        mShopCharges = mResponse.body.shopDetail.shopCharges
        mShippingCharges = mResponse.body.deliveryCharges.value
        txtMerchantChargesTitle.text = mResponse.body.shopDetail.shopName + " Charges"
        txtMerchantCharges.text = String.format("%.2f", shopCharges).toString()
        txtDeliveryCharges.text = String.format("%.2f", deliveryCharges).toString()

        val subtotal = totalItemValue + shopCharges + deliveryCharges
        val subtotalFormat = DecimalFormat("##.##").format(subtotal)
        txtTotal.text = "$$subtotalFormat"
        txtGrandTotal.text = "$$subtotalFormat"
        mTotalAmount = subtotalFormat
        img.loadImage(mResponse.body.shopDetail.shopLogo)
        kfc.text = mResponse.body.shopDetail.shopName
        shopLocation.text = mResponse.body.shopDetail.geoLocation
        currentCartModel.clear()
        currentCartModel.addAll(mResponse.body.cartData)
        cartAdapter.notifyDataSetChanged()
    }

    fun addToCartAPI(productId: String, qty: String) {
        val mainConsumerActivity = activity as MainConsumerActivity
        val hashMap = HashMap<String, RequestBody>()
        hashMap["productId"] = mainConsumerActivity.mUtils.createPartFromString(productId)
        hashMap["quantity"] = mainConsumerActivity.mUtils.createPartFromString(qty)
        viewModel.userAddUpdateToCartAPI(mainConsumerActivity, false, hashMap)
        viewModel.homeResponse.observe(mainConsumerActivity, this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnPreview ->{
                val intent = Intent(context, AddNewCardActivity::class.java)
                intent.putExtra("from","cart")
                startActivity(intent)
            }
            R.id.firstClick -> {
                mPaymentType = "1"
                ivCard.setImageResource(R.drawable.radio_fill)
                ivPaypal.setImageResource(R.drawable.radio_circle)
            }
            R.id.secondClick -> {
                mPaymentType = "3"
                ivCard.setImageResource(R.drawable.radio_circle)
                ivPaypal.setImageResource(R.drawable.radio_fill)
            }
            R.id.btnCheckout -> {
                if(mAddressId.isEmpty() && mSelfPickUp == "0") {
                    AppUtils.showErrorAlert(
                        requireActivity(),
                        "Please add address"
                    )
                }else{
                    val placeOrderModel = PlaceOrderModel(
                        mSelfPickUp,
                        mNetAmount,
                        mArrayLIst,
                        "1",
                        mShippingCharges,
                        mShopCharges,
                        mTotalAmount,
                        mTotalQuantity.toString(),
                        mVendorId,
                        mAddressId
                    )
                        // if (MyApplication.instance.getString("usertype").equals("1")) {
                        if (mPaymentType == "1") {
                            if (cardId == 0) {
                                AppUtils.showErrorAlert(
                                    requireActivity(),
                                    "Please add your card first"
                                )
                            } else {
                                placeOrderModel.paymentMethod = mPaymentType
                                placeOrderModel.cardId = cardId
                                placeOrderApi(placeOrderModel)
                            }

                        } else {
                            placeOrderModel.paymentMethod = mPaymentType
                            // Toast.makeText(this, "In progress", Toast.LENGTH_SHORT).show()
                            getPayPalWebLink()
                            //add paypal functionality
//                        val intent = Intent(mContext, ThankyouActivity::class.java)
//                        startActivity(intent)
                        }


                }
            }
        }
    }
    private fun getPayPalWebLink() {
        val hashMap = HashMap<String, Any>()
        hashMap.put("totalAmount", mTotalAmount)
        driverViewModel.getPayPalWebLink(requireActivity(), true, hashMap)
        driverViewModel.mResponse.observe(this, this)

    }

    private fun placeOrderApi(placeOrderModel: PlaceOrderModel) {
        viewModel.placeOrderApi(requireActivity(), true, placeOrderModel)
    }

    fun showebview(url: String) {
        val llPadding = 30
        val alert: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alert.setTitle("PayPal")
        val ll = LinearLayout(context)
        ll.orientation = LinearLayout.HORIZONTAL
        ll.setPadding(llPadding, llPadding, llPadding, llPadding)
        ll.gravity = Gravity.CENTER
        var llParam = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        ll.layoutParams = llParam

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, llPadding, 0)
        progressBar.layoutParams = llParam

        llParam = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        llParam.gravity = Gravity.CENTER
        val tvText = TextView(context)
        tvText.text = "Please wait..."
        tvText.setTextColor(Color.parseColor("#019243"))
        tvText.textSize = 16f
        tvText.layoutParams = llParam
        progressBar.visibility = View.VISIBLE
        tvText.visibility = View.VISIBLE
        ll.addView(progressBar)
        ll.addView(tvText)
        val wv: WebView = object : WebView(requireContext()) {
            override fun onCheckIsTextEditor(): Boolean {
                return true
            }
        }
        wv.loadUrl(url)
        wv!!.settings.javaScriptEnabled = true
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

                    val placeOrderModel = PlaceOrderModel(
                        mSelfPickUp,
                        mNetAmount,
                        mArrayLIst,
                        "1",
                        mShippingCharges,
                        mShopCharges,
                        mTotalAmount,
                        mTotalQuantity.toString(),
                        mVendorId,
                        mAddressId
                    )

                    placeOrderModel.transactionId = id
                    // mObject!!.transactionId = add transaction id here
                    placeOrderApi(placeOrderModel)
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