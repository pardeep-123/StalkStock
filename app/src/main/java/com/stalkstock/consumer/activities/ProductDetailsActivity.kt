package com.stalkstock.consumer.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stalkstock.R
import com.stalkstock.advertiser.activities.NotificationFirstActivity
import com.stalkstock.api.RestObservable
import com.stalkstock.api.Status
import com.stalkstock.consumer.adapter.ProductsdetailsAdapter
import com.stalkstock.consumer.model.ModelAddToCart
import com.stalkstock.consumer.model.SellerProduct
import com.stalkstock.consumer.model.UserCommonModel
import com.stalkstock.consumer.model.UserVendorsProductList
import com.stalkstock.utils.BaseActivity
import com.stalkstock.utils.loadImage
import com.stalkstock.utils.others.GlobalVariables
import com.stalkstock.viewmodel.HomeViewModel
import com.stalkstock.utils.others.AppUtils
import kotlinx.android.synthetic.main.activity_productdetails.*
import okhttp3.RequestBody
import java.util.*

class ProductDetailsActivity : BaseActivity(), Observer<RestObservable> {
    var context: ProductDetailsActivity? = null
    lateinit var adapter: ProductsdetailsAdapter
    lateinit var productdetails_recycle: RecyclerView
    private var currentProductID = ""
    private var currentDelivery_type = ""
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var currentLowPrice = ""
    var currentHighPrice = "10000"
    var currentSortBy = "high_to_low"//sort by high_to_low => high to low low_to_high =>low to high

    private var reset = false
    private var currentOffset = 0
    private var currentModel: ArrayList<SellerProduct> = ArrayList()


    lateinit var back: ImageView
    lateinit var notification: ImageView
    lateinit var search: ImageView
    lateinit var fillter: ImageView
    lateinit var nsc_top: NestedScrollView
    override fun getContentId(): Int {
        return R.layout.activity_productdetails
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        productdetails_recycle = findViewById(R.id.productdetails_recycle)
        productdetails_recycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    if (currentOffset > 1 && currentModel.size > 4)
                        getProductAsPerVendor()
                }
            }
        })
        back = findViewById(R.id.back)
        notification = findViewById(R.id.notification)
        search = findViewById(R.id.search)
        fillter = findViewById(R.id.fillter)
        nsc_top = findViewById(R.id.nsc_top)

        currentProductID = intent.getStringExtra("product_id")!!
        currentDelivery_type = intent.getStringExtra("deliveryType")!!
        getProductAsPerVendor()
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    currentLowPrice = data!!.getStringExtra("lowPrice")!!
                    currentHighPrice = data.getStringExtra("highPrice")!!
                    currentSortBy = data.getStringExtra("sortBy")!!
                    reset = true
                    getProductAsPerVendor()

                }
            }

        notification.setOnClickListener {
            val intent = Intent(context, NotificationFirstActivity::class.java)
            startActivity(intent)
        }
        search.setOnClickListener {
            val intent = Intent(this, MainConsumerActivity::class.java)
            intent.putExtra("is_open", "2")
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
        fillter.setOnClickListener {
            val intent = Intent(context, FilterActivity::class.java)
            intent.putExtra("from", "ProductdetailsActivity")
            resultLauncher.launch(intent)
        }
        back.setOnClickListener { finish() }

        btnCheckOut.setOnClickListener { openStartInfoApp() }
        adapter = ProductsdetailsAdapter(this, currentModel)
        productdetails_recycle.layoutManager = LinearLayoutManager(context)
        productdetails_recycle.adapter = adapter
        nsc_top.postDelayed({ nsc_top.scrollTo(0, 0) }, 400)
    }

    val viewModel: HomeViewModel by viewModels()

    private fun getProductAsPerVendor() {
        if (reset) {
            currentOffset = 0
            currentModel.clear()
        }
        val hashMap = HashMap<String, RequestBody>()
        hashMap["offset"] = mUtils.createPartFromString(currentOffset.toString())
        hashMap["limit"] = mUtils.createPartFromString("5")
        hashMap["sortBy"] = mUtils.createPartFromString(currentSortBy)
        hashMap["lowPrice"] = mUtils.createPartFromString(currentLowPrice)
        hashMap["highPrice"] = mUtils.createPartFromString(currentHighPrice)
        hashMap["product_id"] = mUtils.createPartFromString(currentProductID)
        hashMap["deliveryType"] = mUtils.createPartFromString(currentDelivery_type)
        viewModel.userGetVendorProductListAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)

    }

    override fun onChanged(it: RestObservable?) {
        when {
            it!!.status == Status.SUCCESS -> {

                if (it.data is UserVendorsProductList) {
                    val mResponse: UserVendorsProductList = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        currentOffset += 5
                        setAdapterData(mResponse)
                    } else {
                      //  AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }

                if (it.data is ModelAddToCart) {
                    val mResponse: ModelAddToCart = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {

                        reset = true
                        getProductAsPerVendor()
                    } else {
                     //   AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }

                if (it.data is UserCommonModel) {
                    val mResponse: UserCommonModel = it.data
                    if (mResponse.code == GlobalVariables.URL.code) {
                        reset = true
                        getProductAsPerVendor()
                    } else {
                        AppUtils.showErrorAlert(this, mResponse.message)
                    }
                }
            }
            it.status == Status.ERROR -> {
                if (it.data != null) {
                   // Toast.makeText(this, it.data as String, Toast.LENGTH_SHORT).show()
                } else {
                   // Toast.makeText(this, it.error!!.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            it.status == Status.LOADING -> {
            }
        }
    }

    private fun setAdapterData(mResponse: UserVendorsProductList) {
        kfc.text = mResponse.body.product.productVendor.shopName
        deliveryTime.text = mResponse.body.product.productVendor.deliveryTime.toString() + " (Delivery time)"
        starCount.text = String.format("%.2f",mResponse.body.product.productVendor.ratingCount.toFloat()) + " Rating, " + String.format("%.2f",mResponse.body.product.productVendor.totalRating.toFloat())
        star.rating = mResponse.body.product.productVendor.ratingCount.toFloat()
        shopLocation.text = mResponse.body.product.productVendor.ShopAddress
        img.loadImage(mResponse.body.product.productVendor.shopLogo)

        currentModel.addAll(mResponse.body.sellerProduct)

        var currentItemCount = 0
        for (i in currentModel) {
            if (i.cart != null)
                currentItemCount += i.cart.quantity
        }

        if (currentItemCount > 0 && currentItemCount==1) {
            all.visibility = View.VISIBLE
            item_count.text = "$currentItemCount Item"

        }else if(currentItemCount>1){
            all.visibility = View.VISIBLE
            item_count.text = "$currentItemCount Items"
        }
        else {
            all.visibility = View.GONE
        }

        adapter.notifyDataSetChanged()
        reset = false
    }

    fun openStartInfoApp() {
        val dialogSuccessful = Dialog(Objects.requireNonNull(this), R.style.Theme_Dialog)
        dialogSuccessful.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogSuccessful.setContentView(R.layout.delivery_successfully_alert)
        dialogSuccessful.setCancelable(false)
        Objects.requireNonNull(dialogSuccessful.window)
            ?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        dialogSuccessful.setCanceledOnTouchOutside(false)
        dialogSuccessful.window!!.setGravity(Gravity.CENTER)
        val btn_ok = dialogSuccessful.findViewById<Button>(R.id.btn_ok)
        btn_ok.setOnClickListener {
            dialogSuccessful.dismiss()
            val intent = Intent(context, MainConsumerActivity::class.java)
            intent.putExtra("is_open", "1")
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
        dialogSuccessful.show()
    }

    fun addToCartAPI(toString: String, qty: String) {
        val hashMap = HashMap<String, RequestBody>()
        hashMap["productId"] = mUtils.createPartFromString(toString)
        hashMap["quantity"] = mUtils.createPartFromString(qty)
        viewModel.userAddUpdateToCartAPI(this, true, hashMap)
        viewModel.homeResponse.observe(this, this)

    }
}
